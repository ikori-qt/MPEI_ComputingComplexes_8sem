import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class assignmentTwoRelays {
    private final int[] possibleVoltages = {3, 6, 10, 20, 35, 110, 220, 330, 500, 750, 1150};
    private Random randomInRelay = new Random();
    private String isProtecting;
    private String uniqueID;
    private Double failRate;
    private int voltageClass;
    private Double workingParameter;
    private String callsFor;

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

    public void setFailRate(Double relativeValue) {this.failRate = relativeValue;}

    public void setWorkingParameter (Double current) {this.workingParameter = current;}

    public void setTarget (String target) {this.isProtecting = target; }

    public String protects () { return this.isProtecting; }

    public void setMeans (String thething) {this.callsFor = thething;}

    public String workedOn () { return this.callsFor; }

    public boolean worksInTheFirstPlace(){
        if ((Double) randomInRelay.nextDouble(1) < failRate) {return false;}
        else {return true;}
    }

    public boolean canCutCurrent(Double whatItSees) {
        if (workingParameter <= whatItSees) {return true;}
        else {return false;}
    }
}
