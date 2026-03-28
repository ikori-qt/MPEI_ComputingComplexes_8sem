import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class assignmentTwoSubstation {
    public ArrayList<assignmentTwoLine> everyLine = new ArrayList<>();
    public ArrayList<assignmentTwoBreaker> everyBreaker = new ArrayList<>();
    public ArrayList<assignmentTwoBus> everyBus = new ArrayList<>();
    public ArrayList<assignmentTwoTransformer> everyTransformer = new ArrayList<>();
    public ArrayList<assignmentTwoRelays> everyProtection = new ArrayList<>();
    private JSONParser parser = new JSONParser();


    public void collectConnectedLines(String JSONAddress) throws IOException, ParseException {
        JSONObject fileObject = (JSONObject) parser.parse(new FileReader(JSONAddress));
        JSONObject substation = (JSONObject) fileObject.get("substation");
        JSONObject lines = (JSONObject) substation.get("lines");
        for (Object lineInternalName : lines.keySet()){
            everyLine.add(new assignmentTwoLine((JSONObject) lines.get(lineInternalName)));
        }
    }

    public void collectConnectedCircuitBreakers (String JSONAddress) throws IOException, ParseException {
        JSONObject fileObject = (JSONObject) parser.parse(new FileReader(JSONAddress));
        JSONObject substation = (JSONObject) fileObject.get("substation");
        JSONObject circuitBreakers = (JSONObject) substation.get("breakers");
        for (Object breakerInternalName : circuitBreakers.keySet()){
            everyBreaker.add(new assignmentTwoBreaker((JSONObject) circuitBreakers.get(breakerInternalName)));
        }
    }

    public void collectConnectedBuses (String JSONAddress) throws IOException, ParseException {
        JSONObject fileObject = (JSONObject) parser.parse(new FileReader(JSONAddress));
        JSONObject substation = (JSONObject) fileObject.get("substation");
        JSONObject buses = (JSONObject) substation.get("buses");
        for (Object busInternalName : buses.keySet()){
            everyBus.add(new assignmentTwoBus((JSONObject) buses.get(busInternalName)));
        }
    }

    public void collectConnectedTransformers (String JSONAddress) throws IOException, ParseException {
        JSONObject fileObject = (JSONObject) parser.parse(new FileReader(JSONAddress));
        JSONObject substation = (JSONObject) fileObject.get("substation");
        JSONObject transformers = (JSONObject) substation.get("transformers");
        for (Object transformerInternalName : transformers.keySet()){
            everyTransformer.add(new assignmentTwoTransformer((JSONObject) transformers.get(transformerInternalName)));
        }
    }

    public void collectConnectedProtections (String JSONAddress) throws IOException, ParseException {
        JSONObject fileObject = (JSONObject) parser.parse(new FileReader(JSONAddress));
        JSONObject substation = (JSONObject) fileObject.get("substation");
        JSONObject protections = (JSONObject) substation.get("protections");
        for (Object protectionInternalName : protections.keySet()){
            if (((String) ((JSONObject)protections.get(protectionInternalName)).get("uniqueID")).contains("OCR")) {
                everyProtection.add(new assignmentTwoProtectionCutoff((JSONObject) protections.get(protectionInternalName)));
            }
            else if (((String) ((JSONObject)protections.get(protectionInternalName)).get("uniqueID")).contains("MTZ")) {
                everyProtection.add(new assignmentTwoProtectionMTZ((JSONObject) protections.get(protectionInternalName)));
            }
            else {
                everyProtection.add(new assignmentTwoProtectionGas((JSONObject) protections.get(protectionInternalName)));
            }
        }
    }
}
