import org.json.simple.JSONObject;


public class assignmentTwoLine extends assignmentTwoParentEquipment{
    private String connectionOne;
    private String connectionTwo;

    public assignmentTwoLine(JSONObject singleLine) {
        this.setUniqueID((String) singleLine.get("uniqueID"));
        this.setHighVoltageClass(((Long) singleLine.get("voltageClass")).intValue());
        this.connectToConnectionOne((String) singleLine.get("connectionOne"));
        this.connectToConnectionTwo((String) singleLine.get("connectionTwo"));
        this.setFirstResponseRelay((String) singleLine.get("protectedFirst"));
        for (Object number : ((JSONObject) singleLine.get("backupProtection")).keySet()) {
            this.setSecondResponse((String) ((JSONObject) singleLine.get("backupProtection")).get(number));
        }
    }

    public void connectToConnectionOne (String connectingEquipmentID) {
        this.connectionOne = connectingEquipmentID;
    }

    public String getConnectionOne() {
        if (this.connectionOne.isEmpty()) {
            return ("Not connected");
        }
        else {
            return this.connectionOne;
        }
    }

    public void connectToConnectionTwo (String connectingEquipmentID) {
        this.connectionTwo = connectingEquipmentID;
    }

    public String getConnectionTwo() {
        if (this.connectionTwo.isEmpty()) {
            return ("Not connected");
        } else {
            return this.connectionTwo;
        }
    }
}
