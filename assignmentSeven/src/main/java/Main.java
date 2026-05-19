import java.util.Scanner;

/**
 * Entry point of Lab 7. Brings up the Plotter window, attaches the Subscriber
 * to the MQTT topic, then runs the Broadcaster which generates the CSV file
 * for the chosen waveform and publishes its rows one by one.
 */
public class Main {

    /**
     * input waveform name: SINE | SQUARE | SAWTOOTH | APERIODIC | DIFFERENTIATING
     */
    public static void main() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter waveform (SINE/SQUARE/SAWTOOTH/APERIODIC/DIFFERENTIATING): ");
        String input = scanner.nextLine().toUpperCase();
        System.out.print("Enter sample amount: ");
        int sampleInput = scanner.nextInt();
        Broadcaster.Waveform waveform = Broadcaster.Waveform.valueOf(input);

        Plotter plotter = new Plotter("Lab 7 — " + waveform.name());
        Subscriber subscriber = new Subscriber(plotter);
        subscriber.start();

        Thread.sleep(1000);

        Broadcaster broadcaster = new Broadcaster(waveform, sampleInput);
        broadcaster.publish();

        subscriber.stop();
    }
}
