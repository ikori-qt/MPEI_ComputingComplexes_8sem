import org.json.simple.JSONObject;

public class assignmentTwoProtectionMTZ  extends assignmentTwoRelays {

    public assignmentTwoProtectionMTZ(JSONObject singleProtection) {
        this.setUniqueID((String) singleProtection.get("uniqueID"));
        this.setHighVoltageClass(((Long) singleProtection.get("voltageClass")).intValue());
        this.setTarget((String) singleProtection.get("protects"));
        this.setWorkingParameter(((Long) singleProtection.get("currentValues")).doubleValue());
        this.setMeans((String) singleProtection.get("triggers"));
        this.setFailRate((Double) singleProtection.get("failRate"));
    }
}