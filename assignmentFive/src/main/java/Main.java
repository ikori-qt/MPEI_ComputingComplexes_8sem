import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main () throws IOException, InterruptedException {
        long startTime = System.nanoTime();

        ObjectMapper mapper = new ObjectMapper();
        CircuitData dataCircOne = mapper.readValue(Files.readString(Path.of("assignmentFive/src/main/java/electricalGraphOne.json")), CircuitData.class);
        Thread t1 = new Thread(dataCircOne::startComputing);
        CircuitData dataCircThree = mapper.readValue(Files.readString(Path.of("assignmentFive/src/main/java/electricalGraphThree.json")), CircuitData.class);
        Thread t2 = new Thread(dataCircThree::startComputing);
        CircuitData dataCircTwo = mapper.readValue(Files.readString(Path.of("assignmentFive/src/main/java/electricalGraphTwo.json")), CircuitData.class);
        Thread t3 = new Thread(dataCircTwo::startComputing);
        ///ASK HOW DOES REPLACING A LAMBDA FUNCTION INTO THIS WORKS


        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        long endTime = System.nanoTime();

        long duration = (endTime - startTime)/1_000_000;
        System.out.println("Execution time: " + duration + "ms");
    }
}
