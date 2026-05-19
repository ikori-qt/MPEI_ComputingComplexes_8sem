import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Swing/JFreeChart window that draws the incoming (x, y) stream. Exposes the
 * primitives used by Subscriber: reset() at the start of a transmission,
 * addPoint() per data row, finish() at the end.
 */
public class Plotter {

    private final XYSeries series = new XYSeries("signal");
    private final JFrame frame;

    /** @param title initial window title */
    public Plotter(String title) {
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                title, "t", "y", dataset);
        frame = new JFrame(title);
        frame.setSize(1200, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new ChartPanel(chart));
        frame.setVisible(true);
    }

    /**
     * Clears the chart for a new transmission.
     *
     * @param waveformName name from the START marker, used as title suffix
     */
    public void reset(String waveformName) {
        SwingUtilities.invokeLater(() -> {
            // invoke later is used to reset UI elements at the end of the transmission
            series.clear();
            frame.setTitle("Lab 7 — " + waveformName);
        });
    }

    /**
     * @param x  value
     * @param y  value
     */
    public void addPoint(double x, double y) {
        SwingUtilities.invokeLater(() -> series.add(x, y));
    }

    /** Called on END marker; logs the count of received points. */
    public void finish() {
        System.out.println("Transmission finished. Points: " + series.getItemCount());
    }
}
