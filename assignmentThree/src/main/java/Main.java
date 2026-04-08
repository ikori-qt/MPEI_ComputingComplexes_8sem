import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main () throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CircuitData dataCircOne = mapper.readValue(Files.readString(Path.of("assignmentThree/src/main/java/electricalGraphOne.json")), CircuitData.class);
        dataCircOne.startComputing();
        CircuitData dataCircThree = mapper.readValue(Files.readString(Path.of("assignmentThree/src/main/java/electricalGraphThree.json")), CircuitData.class);
        dataCircThree.startComputing();
    }
}
