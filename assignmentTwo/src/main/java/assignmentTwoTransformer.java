import org.json.simple.JSONObject;

import java.util.Arrays;

public class assignmentTwoTransformer extends assignmentTwoParentEquipment{
    private String connectionOne;
    private String connectionTwo;
    private int highVoltage;
    private int lowVoltage;

    public assignmentTwoTransformer(JSONObject singleTransformer) {
        this.setUniqueID((String) singleTransformer.get("uniqueID"));
        this.setHighVoltageClass(((Long) singleTransformer.get("highVoltage")).intValue());
        this.setLowVoltageClass(((Long) singleTransformer.get("lowVoltage")).intValue());
        this.connectToConnectionOne((String) singleTransformer.get("connectionOne"));
        this.connectToConnectionTwo((String) singleTransformer.get("connectionTwo"));
        this.setFirstResponseRelay((String) singleTransformer.get("protectedFirst"));
        for (Object number : ((JSONObject) singleTransformer.get("backupProtection")).keySet()) {
            this.setSecondResponse((String) ((JSONObject) singleTransformer.get("backupProtection")).get(number));
        }
    }

    @Override
    public void setHighVoltageClass(int nominalVoltage){
        if (Arrays.stream(this.showPossibleVoltages()).filter(each -> each == nominalVoltage).count() == 1) {
            this.highVoltage = nominalVoltage;
        }
        else{
            System.out.println("Nominal Voltage can not be seen in a list of voltages. An error or a typo may have occurred, no value has been set.");
        }
    }

    @Override
    public int getHighVoltageClass () {return this.highVoltage;}

    public void setLowVoltageClass(int nominalVoltage){
        if (Arrays.stream(this.showPossibleVoltages()).filter(each -> each == nominalVoltage).count() == 1) {
            this.lowVoltage = nominalVoltage;
        }
        else{
            System.out.println("Nominal Voltage can not be seen in a list of voltages. An error or a typo may have occurred, no value has been set.");
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
