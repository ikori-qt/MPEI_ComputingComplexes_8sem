import java.util.List;

class Branch {
    public int id;
    public int startsFrom;
    public int endsIn;
    public List<Element> elements;

    public Double collectedEquivalentResistance;
    public Double collectedTimeDependentEMF = 0.0; //the sum
    public Double currentSourcesSum;

    private Double momentPotentialDifference;
    private Double momentCurrent;

    public void collectResistance(Double deltaT) {
        this.collectedEquivalentResistance = 0.0;
        for (Element each : elements) {
            if (each instanceof Inductance || each instanceof Capacitance) {
                ((RememberDeltaT) each).rememberDeltaT(deltaT); //it forces check on each to see it implements RememberDT then executes it
            }
            each.setEquivalentResistance();
            this.collectedEquivalentResistance += each.getEquivalentResistance();
        }
    }

    /**
     * Should receive the updated versions of the EMFs inside each element, or change them TRIGGERS AUTOMATICALLY in module below
     */
    public void updateAndCollectTimeDependentEMF() {
        if (this.momentPotentialDifference !=null) {
            this.momentCurrent = ((this.momentPotentialDifference + this.collectedTimeDependentEMF) /
                    this.collectedEquivalentResistance) +
                    this.currentSourcesSum;

            this.collectedTimeDependentEMF = 0.0; //the sum
            for (Element each : elements) {
                if (each instanceof Inductance || each instanceof Capacitance) {
                    each.setNewEquivalentEMF(this.momentCurrent);
                }
                this.collectedTimeDependentEMF += each.getEquivalentEmf();
            }
        }
        else {
            this.collectedTimeDependentEMF = 0.0;
            for (Element each : elements) {
                each.setNewEquivalentEMF(0.0);
                this.collectedTimeDependentEMF += each.getEquivalentEmf();
            }
        }
    }

    public void setMomentPotentialDifference (Double newMomentPotentialDifference) {
        this.momentPotentialDifference = newMomentPotentialDifference;
        updateAndCollectTimeDependentEMF();
    }

    public void collectCurrentSources() {
        this.currentSourcesSum = 0.0;
        for (Element each : elements) {
            if (each instanceof CurrentSource) {
                this.currentSourcesSum += ((CurrentSource) each).getJValue();
            }
        }
    }
}