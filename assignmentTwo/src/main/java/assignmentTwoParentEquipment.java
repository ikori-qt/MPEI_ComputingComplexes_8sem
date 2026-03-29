import java.util.ArrayList;
import java.util.Arrays;

public class assignmentTwoParentEquipment implements assignmentTwoStringGetter {
    private final int[] possibleVoltages = {3, 6, 10, 20, 35, 110, 220, 330, 500, 750, 1150};
    private int voltageClass;
    private String uniqueID;
    private String firstResponseRelay;
    private ArrayList<String> secondResponse = new ArrayList<>();


    public void setHighVoltageClass(int nominalVoltage){
        if (Arrays.stream(possibleVoltages).filter(each -> each == nominalVoltage).count() == 1) {
            this.voltageClass = nominalVoltage;
        }
        else{
            System.out.println("Nominal Voltage can not be seen in a list of voltages. An error or a typo may have occurred, no value has been set.");
        }
    }

    public int getHighVoltageClass() {
        return this.voltageClass;
    }

    public void setUniqueID (String ID) {this.uniqueID = ID; }

    public String getUniqueID() {return this.uniqueID;}

    public int[] showPossibleVoltages() { return this.possibleVoltages.clone(); }

    public void setFirstResponseRelay(String relayID) { this.firstResponseRelay = relayID; }

    public String getFirstResponseRelay () { return this.firstResponseRelay; }

    public void setSecondResponse(String relayID) {this.secondResponse.add(relayID);}

    public ArrayList<String> getSecondResponse() {return (ArrayList<String>) this.secondResponse.clone(); } //why do i wrap this as a (ArrayList)?

}