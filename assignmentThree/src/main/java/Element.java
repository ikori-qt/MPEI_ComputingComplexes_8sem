import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Resistance.class, name = "resistance"),
        @JsonSubTypes.Type(value = Inductance.class, name = "inductance"),
        @JsonSubTypes.Type(value = Capacitance.class, name = "capacitance"),
        @JsonSubTypes.Type(value = EMF.class, name = "EMF"),
        @JsonSubTypes.Type(value = CurrentSource.class, name = "currentSource")
})
/**
 * Parent abstract class that sets the framework for auto parsing
 */
abstract class Element {
    @JsonProperty("isAPartOf") protected Integer isAPartOf;
    @JsonProperty("name") protected String name;
    @JsonProperty("R") protected Double resistance;
    @JsonProperty("L") protected Double inductance;
    @JsonProperty("C") protected Double capacitance;
    @JsonProperty("EMF") protected Double emf;
    @JsonProperty("J") protected Double currentSource;

    protected Double deltaTDependentEquivalentResistance;
    protected Double deltaTDependentNextUseEMF;

    public abstract Double getEquivalentEmf();
    public abstract Double getEquivalentResistance();
    public abstract void setEquivalentResistance();
    public abstract void setNewEquivalentEMF(Double createdCurrent);
}

/**
 * Resistance Class
 * No EMF
 * sets deltaTDependentEquivalentResistance to resistance via method equate...
 * deltaTDependentEquivalentResistance accessible via get...
 * does NOT set EquivalentEMF
 */
class Resistance extends Element {

    @Override
    public Double getEquivalentEmf() {
        return 0.0;
    }

    @Override
    public Double getEquivalentResistance() {
        return this.deltaTDependentEquivalentResistance;
    }

    @Override
    public void setEquivalentResistance() {
        this.deltaTDependentEquivalentResistance = this.resistance;
    }

    @Override
    public void setNewEquivalentEMF(Double createdCurrent) {

    }
}

class Inductance extends Element implements RememberDeltaT {
    private Double deltaT;
    private Double startingCurrent = 0.0;
    private Double dependentStartingVoltage = 0.0;

    @Override
    public Double getEquivalentEmf() {
        return this.deltaTDependentNextUseEMF;
    }

    @Override
    public Double getEquivalentResistance() {
        return this.deltaTDependentEquivalentResistance;
    }

    @Override
    public void setEquivalentResistance() {
        this.deltaTDependentEquivalentResistance = 2 * this.inductance / this.deltaT;
    }

    @Override
    public void setNewEquivalentEMF(Double createdCurrent) { /// replaced order *
        this.dependentStartingVoltage = (this.deltaTDependentEquivalentResistance)*
                createdCurrent -
                ((this.deltaTDependentNextUseEMF != null ) ? this.deltaTDependentNextUseEMF : 0.0);

        this.deltaTDependentNextUseEMF = (this.deltaTDependentEquivalentResistance)*
                createdCurrent +
                this.dependentStartingVoltage;

        this.startingCurrent = createdCurrent;
    }

    @Override
    public void rememberDeltaT(Double deltaT) {
        this.deltaT = deltaT;
    }
}

class Capacitance extends Element implements RememberDeltaT {
    private Double deltaT;
    private Double startingVoltage = 0.0;
    private Double dependentStartingCurrent = 0.0;

    @Override
    public Double getEquivalentEmf() {
        return this.deltaTDependentNextUseEMF;
    }

    @Override
    public Double getEquivalentResistance() {
        return this.deltaTDependentEquivalentResistance;
    }

    @Override
    public void setEquivalentResistance() {
        this.deltaTDependentEquivalentResistance = this.deltaT / (2 * this.capacitance);
    }

    /**
     * Here instead of using a controlled parameter (Voltage) we cheat by using current?
     * @param createdCurrent - got from a potential difference + EMF / eqResistance
     */
    @Override
    public void setNewEquivalentEMF(Double createdCurrent) { /// order changed here too
        this.startingVoltage = this.deltaTDependentEquivalentResistance *
                createdCurrent -
                ((this.deltaTDependentNextUseEMF != null ) ? this.deltaTDependentNextUseEMF : 0.0);

        this.deltaTDependentNextUseEMF = (-this.deltaTDependentEquivalentResistance)*
                createdCurrent -
                this.startingVoltage;

        this.dependentStartingCurrent = createdCurrent;
    }

    @Override
    public void rememberDeltaT(Double deltaT) {
        this.deltaT = deltaT;
    }
}

class EMF extends Element {
    @JsonCreator
    public EMF(@JsonProperty("R") Double resistance) {
        this.resistance = (resistance != null) ? resistance : 0.000001; //ternary operator == if else
    }

    @Override
    public Double getEquivalentResistance() {
        return this.deltaTDependentEquivalentResistance;
    }

    @Override
    public Double getEquivalentEmf() {
        return this.emf;
    }

    @Override
    public void setEquivalentResistance() {
        this.deltaTDependentEquivalentResistance = this.resistance;
    }

    @Override
    public void setNewEquivalentEMF(Double createdCurrent) { //can be made to emulate a sin
    }
}

class CurrentSource extends Element {
    @JsonCreator
    public CurrentSource(@JsonProperty("R") Double resistance) {
        this.resistance = (resistance != null) ? resistance : 1000000; //ternary operator == if else  WHY THO
    }

    @Override
    public Double getEquivalentEmf() {
        return 0.0;
    }

    @Override
    public Double getEquivalentResistance() {
        return this.deltaTDependentEquivalentResistance;
    }

    @Override
    public void setEquivalentResistance() {
        this.deltaTDependentEquivalentResistance = this.resistance;
    }

    @Override
    public void setNewEquivalentEMF(Double createdCurrent) {
    }

    public Double getJValue() {
        return this.currentSource;
    }
}
