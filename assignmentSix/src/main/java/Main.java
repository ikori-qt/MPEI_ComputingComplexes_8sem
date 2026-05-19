import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    static void main () throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        GridData dataGridOne = mapper.readValue(Files.readString(Path.of("assignmentSix/src/main/resources/TestGrid1.json")), GridData.class);

        //dataCircOne.startComputing();
    }
}
