import java.util.Arrays;

public class assignmentTwoParentEquipment {
    private final int[] possibleVoltages = {3, 6, 10, 20, 35, 110, 220, 330, 500, 750, 1150};
    private int voltageClass;
    private String connectionOne;
    private String connectionTwo;
    private String uniqueID;

    public void setVoltageClass (int nominalVoltage){
        if (Arrays.stream(possibleVoltages).filter(each -> each == nominalVoltage).count() == 1) {
            voltageClass = nominalVoltage;
        }
        else{
            System.out.println("Nominal Voltage can not be seen in a list of voltages. An error or a typo may have occurred, no value has been set.");
        }
    }

    public int getVoltageClass() {
        return voltageClass;
    }

    public void connectToConnectionOne (String connectingEquipmentID) {
        connectionOne = connectingEquipmentID;
    }

    public String getConnectionOne() {
        if (connectionOne.isEmpty()) {
            return ("Not connected");
        }
        else {
            return connectionOne;
        }
    }
}
