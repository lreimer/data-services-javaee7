package de.qaware.oss.cloud.source.mqtt;

import fish.payara.cloud.connectors.mqtt.api.MQTTConnection;
import fish.payara.cloud.connectors.mqtt.api.MQTTConnectionFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.Json;
import javax.json.JsonWriter;
import javax.resource.ConnectionFactoryDefinition;
import javax.resource.spi.TransactionSupport;
import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@ConnectionFactoryDefinition(name = "java:app/mqtt/factory",
        description = "MQTT Connection Factory",
        interfaceName = "fish.payara.cloud.connectors.mqtt.api.MQTTConnectionFactory",
        resourceAdapter = "mqtt-rar-0.1.0",
        minPoolSize = 2,
        transactionSupport = TransactionSupport.TransactionSupportLevel.NoTransaction,
        maxPoolSize = 2,
        properties = {
                "serverURIs=tcp://eclipse-mosquitto:1883",
                "cleanSession=true"
        })
@Singleton
@Startup
public class MqttMessenger {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Resource(lookup = "java:app/mqtt/factory")
    MQTTConnectionFactory factory;

    private Random random;

    @PostConstruct
    public void initialise() {
        random = new Random();
    }

    @Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
    public void fireEvent() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        JsonWriter writer = Json.createWriter(outputStream);
        writer.writeObject(Json.createObjectBuilder()
                .add("vin", "vin-" + random.nextInt(42))
                .add("latitude", random.nextDouble())
                .add("longitude", random.nextDouble())
                .build());
        writer.close();

        publish(outputStream.toByteArray());
    }

    public void publish(byte[] payload) {
        try (MQTTConnection conn = factory.getConnection()) {
            conn.publish("de/qaware/oss/cloud/mqtt", payload, 1, false);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to publish MQTT message.", e);
        }
    }
}
