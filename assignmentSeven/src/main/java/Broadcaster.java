import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Generates a sampled waveform, writes it to CSV, then publishes each (t, y)
 * pair to an MQTT topic surrounded by START / END marker messages so that the
 * Subscriber can detect transmission boundaries.
 */
public class Broadcaster {

    private static final String BROKER = "tcp://localhost:1883";
    private static final String TOPIC = "iot_data";

    /** Enum function library returning values at t with a passed case. */
    public enum Waveform {
        SINE,
        SQUARE,
        SAWTOOTH,
        APERIODIC,
        DIFFERENTIATING;

        /**
         * @param t time argument
         * @return waveform value at t (w = 2π50, phi = 0, k = T = 1)
         */
        double valueAt(double t) {
            double omega = 2 * Math.PI * 1;
            double phi = 0.0;
            double k = 1.0;
            double T = 1.0;
            switch (this) {
                case SINE:
                    return Math.sin(omega * t + phi);
                case SQUARE:
                    return Math.signum(Math.sin(omega * t + phi));
                case SAWTOOTH:
                    double sum = 0.0;
                    for (int n = 1; n <= 20; n++) {
                        sum += Math.pow(-1, n + 1) / n * Math.sin(n * t);
                    }
                    return (2.0 / Math.PI) * sum;
                case APERIODIC:
                    return k * (1.0 - Math.exp(-t / T));
                case DIFFERENTIATING:
                    return (k / T) * Math.exp(-t / T);
                default:
                    return 1;
            }
        }
    }

    private Waveform waveform;

    /** CONSTRUCTOR SPECIFYING THE WAVEFORM
     * @param waveform variant to emit */
    public Broadcaster(Waveform waveform, int samples) throws IOException {
        this.waveform = waveform;
        generateList(samples);
    }

    /**
     * Samples the waveform uniformly over t ∈ [0, 4π].
     *
     * @param samples number of samples to generate
     */
    private void generateList(int samples) throws IOException {
        PrintWriter writerCSV = new PrintWriter(Files.newBufferedWriter(Path.of("D:\\Пользователи\\User\\Desktop\\MPEI_ВычислитТехн_8sem\\Assignments\\Assignments\\assignmentSeven\\src\\main\\resources\\tempCSV.csv")));
        for (int i = 0; i < samples; i++) {
            double t = 4.0 * Math.PI * i / samples;
            writerCSV.println(t + "," + waveform.valueAt(t));
        }
        writerCSV.close();
    }

    /**
     * Publishes START, then each "x,y" row, then END.
     *
     */
    public void publish() throws MqttException, InterruptedException, IOException {
        String clientId = "broadcaster-" + 156;
        MqttClient client = new MqttClient(BROKER, clientId);
        client.connect();

        client.publish(TOPIC, new MqttMessage(("START:" + waveform.name()).getBytes()));

        List<String> csvLines = Files.readAllLines(Paths.get("D:\\Пользователи\\User\\Desktop\\MPEI_ВычислитТехн_8sem\\Assignments\\Assignments\\assignmentSeven\\src\\main\\resources\\tempCSV.csv"));
        for (String line : csvLines) {
            client.publish(TOPIC, new MqttMessage(line.getBytes()));
            Thread.sleep(20); // pacing so the plot updates progressively
        }

        client.publish(TOPIC, new MqttMessage("END".getBytes()));
        client.disconnect();
    }
}
