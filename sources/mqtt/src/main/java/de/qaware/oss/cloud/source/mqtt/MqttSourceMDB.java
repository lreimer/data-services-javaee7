package de.qaware.oss.cloud.source.mqtt;

import fish.payara.cloud.connectors.mqtt.api.MQTTListener;
import fish.payara.cloud.connectors.mqtt.api.OnMQTTMessage;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "serverURIs", propertyValue = "tcp://eclipse-mosquitto:1883"),
        @ActivationConfigProperty(propertyName = "cleanSession", propertyValue = "false"),
        @ActivationConfigProperty(propertyName = "automaticReconnect", propertyValue = "true"),
        @ActivationConfigProperty(propertyName = "filePersistence", propertyValue = "false"),
        @ActivationConfigProperty(propertyName = "connectionTimeout", propertyValue = "30"),
        @ActivationConfigProperty(propertyName = "maxInflight", propertyValue = "3"),
        @ActivationConfigProperty(propertyName = "keepAliveInterval", propertyValue = "5"),
        @ActivationConfigProperty(propertyName = "topicFilter", propertyValue = "de/qaware/oss/cloud/mqtt"),
        @ActivationConfigProperty(propertyName = "qos", propertyValue = "1")
})
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
public class MqttSourceMDB implements MQTTListener {

    private static final Logger LOGGER = Logger.getLogger(MqttSourceMDB.class.getName());

    @Inject
    VehicleLocationTopic vehicleLocation;

    @OnMQTTMessage
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    @Transactional(Transactional.TxType.REQUIRED)
    public void onMQTTMessage(String topic, MqttMessage message) {

        JsonReader reader = Json.createReader(new ByteArrayInputStream(message.getPayload()));
        JsonObject jsonObject = reader.readObject();

        LOGGER.log(Level.INFO, "Received MQTT message {0} on topic {1}", new Object[]{jsonObject, topic});
        vehicleLocation.publish(jsonObject);
    }
}
