import java.util.HashMap;

public class DocData {
    private HashMap<String, Double> indResistanceByLength;
    private HashMap<String, Double[]> indParametersForTransformers;

    public DocData() {
        indResistanceByLength = new HashMap<>();
        indParametersForTransformers = new HashMap<>();

        indResistanceByLength.put("АС 120/19", 0.427);
        indResistanceByLength.put("АС 185/24", 0.413);

        indParametersForTransformers.put("ТДЦ-80000/220 У(ХЛ)", new Double[]{11.0, 242.0, 80.0});
    }

    public Double getLineDocIndResistance(String type) {
        return indResistanceByLength.getOrDefault(type, 0.4);
    }

    public Double[] getTransformerDocParams(String type) {
        return indParametersForTransformers.get(type);
    }
}
