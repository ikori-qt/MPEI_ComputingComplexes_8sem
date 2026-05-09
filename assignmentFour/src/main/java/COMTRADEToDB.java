import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.List;

public class COMTRADEToDB {
    private String dataName;
    private int analogCount;
    private int digitalCount;
    private String dbURL;


    private void createDB() throws SQLException {
        this.dbURL = "jdbc:sqlite:assignmentFour/src/main/java/exampleComtrade.db";
        Connection connection = DriverManager.getConnection(dbURL);
        Statement statement = connection.createStatement();
        statement.execute("""
                CREATE TABLE IF NOT EXISTS ConfigFor""" + this.dataName +
                """
                            (id INTEGER PRIMARY KEY AUTOINCREMENT,
                            station_name TEXT,
                            total_channels INTEGER,
                            analog_channels INTEGER,
                            digital_channels INTEGER,
                            frequency INTEGER,
                            sample_rate INTEGER,
                            end_sample INTEGER
                            );
                        """);

        /**
         * Constructing a STRING to put it inside the execute command... the EASY way
         */
        StringBuilder sqlDataCommand = new StringBuilder("""
                CREATE TABLE IF NOT EXISTS DataFor"""
                + this.dataName
                + """
                (id INTEGER PRIMARY KEY AUTOINCREMENT,
                timestamp INTEGER
                """ );
        for (int i = 0; i < this.analogCount; i++) {
            sqlDataCommand.append(", channel").append(i + 1).append(" REAL");
        }
        for (int i = 0; i < this.digitalCount; i++) {
            sqlDataCommand.append(", digChannel").append(i + 1).append(" INTEGER");
        }
        sqlDataCommand.append(")");
        statement.execute(sqlDataCommand.toString());
        // John Claude said i need to close the statement for some ununderstood reason
        //statement.close();
        connection.close();
    }


    private void insertConfigData(List<String> configText) throws SQLException {
        Connection connection = DriverManager.getConnection(this.dbURL);
        String sqlCommandBlueprint = "INSERT INTO ConfigFor" + this.dataName + """
                    (station_name,
                    total_channels,
                    analog_channels,
                    digital_channels,
                    frequency,
                    sample_rate,
                    end_sample)
                """ + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement sqlStatement = connection.prepareStatement(sqlCommandBlueprint);
        sqlStatement.setString(1, this.dataName);
        sqlStatement.setInt(2, this.analogCount + this.digitalCount);
        sqlStatement.setInt(3, this.analogCount);
        sqlStatement.setInt(4, this.digitalCount);
        //for frequency
        String tempFrequency = configText.get(2 + this.digitalCount + this.analogCount);
        // cast (int) is required to account for "50" or "50.0"
        sqlStatement.setInt(5, (int) Double.parseDouble(tempFrequency));
        //for sample_rate and end
        String[] tempSampleRate = configText.get(2 + this.digitalCount + this.analogCount + 2).split(",");
        sqlStatement.setInt(6, Integer.parseInt(tempSampleRate[0].trim()));
        sqlStatement.setInt(7, Integer.parseInt(tempSampleRate[1].trim()));
        sqlStatement.execute();
        sqlStatement.close();
        connection.close();
    }


    private void insertTimeData(String dataFileAddress) throws SQLException, IOException {
        StringBuilder sqlCommandBlueprint = new StringBuilder("INSERT INTO DataFor" + dataName + " (timestamp");

        // List the columns we are providing
        for (int i = 0; i < this.analogCount; i++) sqlCommandBlueprint.append(", channel").append(i + 1);
        for (int i = 0; i < this.digitalCount; i++) sqlCommandBlueprint.append(", digChannel").append(i + 1);

        sqlCommandBlueprint.append(") VALUES (?"); // Placeholder for timestamp
        int tempTotalParams = this.analogCount + this.digitalCount;
        for (int i = 0; i < tempTotalParams; i++) sqlCommandBlueprint.append(", ?");
        sqlCommandBlueprint.append(")");

        Connection connection = DriverManager.getConnection(this.dbURL);
        PreparedStatement sqlStatement = connection.prepareStatement(sqlCommandBlueprint.toString());

        /**
         * Using Streams to avoid copying everything into RAM at once
         */
        /**
         * Streams require try catch? I ve been avoiding them because there was no time to get deep into them
         * The try-with-resources guarantees the stream is closed even if an exception is thrown mid-iteration,
         * which also ensures the underlying file handle is released promptly.
         * c - John ClaudeSonnet4
         */
        try (var lines = Files.lines(Path.of(dataFileAddress))) {
            lines.forEach(line -> {
                String[] instanceValues = line.split("\\s*,\\s*");
                try {
                    setValuesIntoStatement(sqlStatement, instanceValues);
                    sqlStatement.execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        connection.close();
    }


    private void setValuesIntoStatement(PreparedStatement statement, String[] instanceValues) throws SQLException {
        for (int i = 1; i < instanceValues.length; i++) {
            statement.setDouble(i, Double.parseDouble(instanceValues[i]));
        }
    }


    public String getDbURL(){
        return this.dbURL;
    }


    public void parseBoth(String configAddress, String timeDataAddress) throws IOException, SQLException {
        List<String> configTextLinesByOne = Files.readAllLines(Path.of(configAddress));

        String[] firstLine = configTextLinesByOne.get(0).split(",");
        this.dataName = firstLine[0].trim();

        String[] secondLine = configTextLinesByOne.get(1).split(",");
        this.analogCount = Integer.parseInt(secondLine[1].replaceAll("[^0-9]", "")); //replace NOT zero to nine with nothing
        this.digitalCount = Integer.parseInt(secondLine[2].replaceAll("[^0-9]", ""));

        createDB();
        insertConfigData(configTextLinesByOne);
        insertTimeData(timeDataAddress);
    }
}