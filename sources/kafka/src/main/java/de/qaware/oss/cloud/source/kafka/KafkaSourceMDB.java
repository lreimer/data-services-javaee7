package de.qaware.oss.cloud.source.kafka;

import fish.payara.cloud.connectors.kafka.api.KafkaListener;
import fish.payara.cloud.connectors.kafka.api.OnRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

@MessageDriven(name = "KafkaSourceMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "clientId", propertyValue = "KafkaSourceMDB"),
        @ActivationConfigProperty(propertyName = "groupIdConfig", propertyValue = "location-consumer-group"),
        @ActivationConfigProperty(propertyName = "topics", propertyValue = "location"),
        @ActivationConfigProperty(propertyName = "bootstrapServersConfig", propertyValue = "kafka:9092"),
        @ActivationConfigProperty(propertyName = "autoCommitInterval", propertyValue = "100"),
        @ActivationConfigProperty(propertyName = "retryBackoff", propertyValue = "1000"),
        @ActivationConfigProperty(propertyName = "keyDeserializer", propertyValue = "org.apache.kafka.common.serialization.StringDeserializer"),
        @ActivationConfigProperty(propertyName = "valueDeserializer", propertyValue = "org.apache.kafka.common.serialization.StringDeserializer"),
        @ActivationConfigProperty(propertyName = "pollInterval", propertyValue = "1000"),
})
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
public class KafkaSourceMDB implements KafkaListener {

    private static final Logger LOGGER = Logger.getLogger(KafkaSourceMDB.class.getName());

    @Inject
    private VehicleLocationTopic vehicleLocation;

    @OnRecord(topics = {"location"})
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void onKafkaConsumerRecord(ConsumerRecord record) {
        LOGGER.log(Level.INFO, "Received Kafka record {0}.", record);

        JsonReader reader = Json.createReader(new StringReader((String) record.value()));
        JsonObject jsonObject = reader.readObject();

        vehicleLocation.publish(jsonObject);
    }
}
