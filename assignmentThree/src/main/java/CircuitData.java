import com.fasterxml.jackson.annotation.JsonProperty;
import org.ejml.simple.SimpleMatrix;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class CircuitData {
    @JsonProperty("deltaT") private Double deltaT;
//    private Double deltaT;
    @JsonProperty("calculationLength") private Double calculationLength;
//    private Double calculationLength;
    @JsonProperty("branches") private List<Branch> branches;
//    private List<Branch> branches;
    private SimpleMatrix Y;
    private SimpleMatrix A;
    private SimpleMatrix J;
    private SimpleMatrix updEMFs;
    private SimpleMatrix momentPotentials;
    private SimpleMatrix momentPotentialDifferences;
//    public Double[][] compiledPotentialDifferences;

    private XYSeries series = new XYSeries("Voltage Over Time");

    private void createConductanceDiagonalMatrix() {
        int tempSize = this.branches.size();

        SimpleMatrix YNew = new SimpleMatrix(tempSize, tempSize);
        for (Branch each : this.branches){
            each.collectResistance(this.deltaT);
            YNew.set(each.id, each.id, 1/each.collectedEquivalentResistance);
        }
        this.Y = YNew;
    }

    private void createConnectionMatrix() {
        int tempWidth = this.branches.size();

        Set<Integer> tempGettingUniquePoints = new HashSet<>();
        for (Branch each : branches) {
            tempGettingUniquePoints.add(each.startsFrom);
            tempGettingUniquePoints.add(each.endsIn);
        }
        int tempDepth = tempGettingUniquePoints.size()-1; //excluding the phi == 0 from the matrix

        SimpleMatrix ANew = new SimpleMatrix(tempDepth, tempWidth);
        for (Branch each : branches) {
            if (each.startsFrom != 0) {ANew.set(each.startsFrom-1, each.id, 1);} // -1 to shift everything up within size
            if (each.endsIn != 0) {ANew.set(each.endsIn-1, each.id, -1);}
        }
        this.A = ANew;
    }

    private void createJMatrix() {
        int tempDepth = this.branches.size();

        SimpleMatrix Jew = new SimpleMatrix(tempDepth,1);
        for (Branch each : branches) {
            each.collectCurrentSources();
            Jew.set(each.id, 0, each.currentSourcesSum);
        }
        this.J = Jew;
    }

    /**
     * Activates after all data about potential differences was transferred and all the EqEMFs recalculated.
     * EVERYTHING INSIDE SHOULD BE UPDATED
     */
    private void updateEMFMatrix() {
        if (updEMFs == null) {
            updEMFs = new SimpleMatrix( this.branches.size(),1);
        }

        for (Branch each : branches) {
//            each.updateAndCollectTimeDependentEMF();
            this.updEMFs.set(each.id,0, (each.collectedTimeDependentEMF != null) ? each.collectedTimeDependentEMF : 0.0);
        }
    }

    private void nullifyEMFs() {
        if (updEMFs != null) {
            updEMFs.fill(0.0);
        }
    }

    private void calculatePotentials(Double currentTime) {
        SimpleMatrix leftEquationSide = this.A
                .mult(this.Y)
                .mult(this.A.transpose());

        SimpleMatrix rightEquationSide = this.A
                .scale(-1)
                .mult(this.Y
                        .mult(this.updEMFs)
                        .plus(this.J));

        System.out.println(updEMFs);

        this.momentPotentials = leftEquationSide
                .solve(rightEquationSide);

        this.momentPotentialDifferences = this.A
                .transpose()
                .mult(this.momentPotentials);

        for (int i = 0; i < momentPotentialDifferences.numRows(); i++) {
            if (i == 1) {
                this.series.add(currentTime.doubleValue(), momentPotentialDifferences.get(i,0));
            }
            branches.get(i).setMomentPotentialDifference(momentPotentialDifferences.get(i,0));
        }
        updateEMFMatrix();
    }

    public void startComputing() {
        this.createConnectionMatrix();
        this.createConductanceDiagonalMatrix();
        System.out.println(Y);
        this.createJMatrix();
        this.updateEMFMatrix();

//        this.compiledPotentialDifferences = new Double[this.branches.size()][(int) (this.calculationLength / this.deltaT)];

        for (Double time = 0.0; time < calculationLength; time += deltaT) {
            calculatePotentials(time);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Circuit Simulation", "Time (s)", "Voltage (V)", dataset);

        ChartFrame frame = new ChartFrame("Results", chart);
        frame.pack();
        frame.setVisible(true);
        this.nullifyEMFs();

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
    }
}