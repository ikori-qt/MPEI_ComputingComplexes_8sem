import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Subscribes to the MQTT topic and dispatches incoming payloads to the Plotter.
 * Distinguishes three kinds of messages: a "START:<name>" marker that begins a
 * transmission, an "END" marker that closes it, and ordinary "x,y" data rows.
 */
public class Subscriber {

    private static final String BROKER = "tcp://localhost:1883";
    private static final String TOPIC = "iot_data";
    private MqttClient client;

    private final Plotter plotter;

    /** @param plotter target plotter that will receive parsed points */
    public Subscriber(Plotter plotter) {
        this.plotter = plotter;
    }

    /**
     * Connects to the broker, subscribes to the topic and registers a callback
     * that routes payloads to plotter.reset / plotter.addPoint / plotter.finish.
     */
    public void start() throws MqttException {
        String clientId = "subscriber-" + 161;
        this.client = new MqttClient(BROKER, clientId);
        this.client.connect();

        this.client.subscribe(TOPIC, (topic, msg) -> {
            //matching signature of received message (topic, message)
            String payload = new String(msg.getPayload());
            if (payload.startsWith("START")) {
                String name = payload.contains(":") ? payload.substring(payload.indexOf(':') + 1) : "";
                plotter.reset(name);
            } else if (payload.equals("END")) {
                plotter.finish();
            } else {
                String[] xy = payload.split(",");
                if (xy.length == 2) {
                    plotter.addPoint(Double.parseDouble(xy[0]), Double.parseDouble(xy[1]));
                }
            }
        });
    }

    public void stop() throws MqttException {
        client.disconnect();
    }
}