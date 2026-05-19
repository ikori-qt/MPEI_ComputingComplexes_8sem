import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ExternalGridEquivalent.class, name = "equivalent"),
        @JsonSubTypes.Type(value = Line.class, name = "lines"),
        @JsonSubTypes.Type(value = Transformer.class, name = "transformers"),
})
/**
 * Parent abstract class that sets the framework for auto parsing
 */
abstract class Element {
    @JsonProperty("id") protected Integer id;
    @JsonProperty("startsFrom") protected Integer startsFrom;
    @JsonProperty("endsIn") protected Integer endsIn;
    public Double indResistance;

    public abstract void computeEquivalents(DocData data);
}


class ExternalGridEquivalent extends Element {
    @JsonProperty("SShortCircuit") private double sShortCircuit;
    @JsonProperty("nominalVoltageInKv") private double nominalVoltageInKv;

    @Override
    public void computeEquivalents(DocData data) {
        this.indResistance = Math.pow(this.nominalVoltageInKv, 2) / this.sShortCircuit;
    }
}

class Line extends Element {
    @JsonProperty("lengthInKm") private double lengthInKm;
    @JsonProperty("type") private String type;

    @Override
    public void computeEquivalents(DocData data) {
        this.indResistance = data.getLineDocIndResistance(this.type) * this.lengthInKm;
    }
}

class Transformer extends Element {
    @JsonProperty("type") private String type;

    @Override
    public void computeEquivalents(DocData data) {
        Double[] tempData = data.getTransformerDocParams(this.type);

        double uk = tempData[0]; //in %
        double uNom = tempData[1]; //in kV
        double sNom = tempData[2]; // in MVA

        this.indResistance = (uk / 100.0) * (Math.pow(uNom, 2) / sNom);
    }
}