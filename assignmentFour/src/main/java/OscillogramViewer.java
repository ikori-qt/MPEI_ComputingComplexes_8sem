import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.sql.*;

public class OscillogramViewer {
    public static void createAnOscillogram(String dbURL, String tableName, String measurementID, int timeStart, int timeFinish) throws SQLException {
        XYSeries seriesForOscillogram = new XYSeries("SeriesFrom "+tableName);

        Connection connection = DriverManager.getConnection(dbURL);
        Statement statement = connection.createStatement();
        //execute returns true or false, query returns things
        ResultSet resultsTable = statement.executeQuery("""
                    SELECT timestamp, 
                """ + measurementID + """
                    FROM
                """ + tableName + """
                    WHERE timestamp BETWEEN 
                """ + timeStart + " AND " + timeFinish + """
                    ORDER BY id ASC
                """);

        while (resultsTable.next()) {
            double x = resultsTable.getDouble("timestamp");
            double y = resultsTable.getDouble(measurementID);
            seriesForOscillogram.add(x, y);
        }
        connection.close();

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesForOscillogram);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Currents in a window of time", "Time (s)", "Current (A)", dataset);

        ChartFrame frame = new ChartFrame("Results", chart);
        frame.pack();
        frame.setVisible(true);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
    }
}
