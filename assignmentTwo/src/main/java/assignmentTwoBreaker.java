import org.json.simple.JSONObject;

public class assignmentTwoBreaker extends assignmentTwoParentEquipment{
    private String connectionOne;
    private String connectionTwo;
    private boolean state;

    public assignmentTwoBreaker(JSONObject singleBreaker) {
        this.setUniqueID((String) singleBreaker.get("uniqueID"));
        this.setHighVoltageClass(((Long) singleBreaker.get("voltageClass")).intValue());
        this.connectToConnectionOne((String) singleBreaker.get("connectionOne"));
        this.connectToConnectionTwo((String) singleBreaker.get("connectionTwo"));
        this.setState((String) singleBreaker.get("state"));
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

    public void setState(String value) {
        if (value.equals("on")) {this.state = true;}
        else {this.state = false;}
    }
}
