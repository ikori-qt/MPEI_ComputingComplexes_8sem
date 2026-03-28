import org.json.simple.JSONObject;

import java.util.ArrayList;

public class assignmentTwoBus extends assignmentTwoParentEquipment{
    private ArrayList<String> connections = new ArrayList<>();

    public assignmentTwoBus(JSONObject singleBus) {
        this.setUniqueID((String) singleBus.get("uniqueID"));
        this.setHighVoltageClass(((Long) singleBus.get("voltageClass")).intValue());
        JSONObject insideConnections = (JSONObject) singleBus.get("connections");
        for (Object connectionInternalName : insideConnections.keySet()){
            connections.add((String) insideConnections.get(connectionInternalName));
        }
        this.setFirstResponseRelay((String) singleBus.get("protectedFirst"));
        for (Object number : ((JSONObject) singleBus.get("backupProtection")).keySet()) {
            this.setSecondResponse((String) ((JSONObject) singleBus.get("backupProtection")).get(number));
        }
    }

    public void changeConnection (int position, String newConnection) {
        this.connections.set(position, newConnection);
    }

    public ArrayList<String> getConnections() {
        return (ArrayList<String>) connections.clone();
    }
}
