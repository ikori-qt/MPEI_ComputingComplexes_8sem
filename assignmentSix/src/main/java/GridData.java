import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GridData {
    @JsonProperty("equivalent")
    private ExternalGridEquivalent equivalent;

    @JsonProperty("lines")
    private List<Line> lines;

    @JsonProperty("transformers")
    private List<Transformer> transformers;

    @JsonProperty("shortCircuitPoint")
    private int shortCircuitPoint;


}
