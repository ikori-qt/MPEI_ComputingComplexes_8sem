import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main () throws IOException, SQLException {
        //COMTRADEToDB handler = new COMTRADEToDB();
        //handler.parseBoth("assignmentFour/src/main/java/TestSignals2.cfg","assignmentFour/src/main/java/TestSignals2.dat");
        OscillogramViewer.createAnOscillogram(//handler.getDbURL(),
                "jdbc:sqlite:assignmentFour/src/main/java/exampleComtrade.db",
                "DataForEMTDC_Simulation",
                "channel1",
                0,
                1999000);
        OscillogramViewer.createAnOscillogram(//handler.getDbURL(),
                "jdbc:sqlite:assignmentFour/src/main/java/exampleComtrade.db",
                "DataForEMTDC_Simulation",
                "channel2",
                0,
                1999000);
        OscillogramViewer.createAnOscillogram(//handler.getDbURL(),
                "jdbc:sqlite:assignmentFour/src/main/java/exampleComtrade.db",
                "DataForSignals",
                "channel1",
                0,
                1999000);
        OscillogramViewer.createAnOscillogram(//handler.getDbURL(),
                "jdbc:sqlite:assignmentFour/src/main/java/exampleComtrade.db",
                "DataForSignals",
                "channel2",
                0,
                1999000);
    }
}
