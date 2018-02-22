package de.qaware.oss.cloud.processor.weather;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.jms.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class CurrentWeatherTopic {
    private static final Logger LOGGER = Logger.getLogger(CurrentWeatherTopic.class.getName());

    @Resource(lookup = "jms/activeMqConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/CurrentWeather")
    private Topic topic;

    public void subscribe(@Observes CurrentWeatherEvent event) {
        publish(event.toJson());
    }

    private void publish(JsonObject weatherData) {
        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(topic);

            StringWriter payload = new StringWriter();
            JsonWriter jsonWriter = Json.createWriter(payload);
            jsonWriter.writeObject(weatherData);

            producer.send(session.createTextMessage(payload.toString()));
        } catch (JMSException e) {
            LOGGER.log(Level.WARNING, "Could not send JMS message.", e);
        }
    }
}
