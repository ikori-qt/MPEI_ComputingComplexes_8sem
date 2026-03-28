import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static assignmentTwoSubstation executeSetting (String JSONAddress) throws IOException, ParseException {
        assignmentTwoSubstation substation = new assignmentTwoSubstation();
        substation.collectConnectedLines(JSONAddress);
        substation.collectConnectedCircuitBreakers(JSONAddress);
        substation.collectConnectedBuses(JSONAddress);
        substation.collectConnectedTransformers(JSONAddress);
        substation.collectConnectedProtections(JSONAddress);
        return substation;
    }


    static void main(String[] args) throws IOException, ParseException {
        String JSONAddress = "D:\\Пользователи\\User\\Desktop\\MPEI_ВычислитТехн_8sem\\Assignments\\Assignments\\assignmentTwo\\src\\main\\java\\assignmentTwoStructure.json";
        int amount = 10;
        assignmentTwoSubstation substation = executeSetting(JSONAddress);
        assignmentTwoShortCircuit shorter = new assignmentTwoShortCircuit();
        for (int i = 0; i< amount; i++) {
            shorter.testEquipment(substation.everyLine, substation.everyBus, substation.everyTransformer, substation.everyProtection);
        }
    }
}
