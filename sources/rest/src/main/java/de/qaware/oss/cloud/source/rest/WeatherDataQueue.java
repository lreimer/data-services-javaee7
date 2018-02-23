package de.qaware.oss.cloud.source.rest;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class WeatherDataQueue {
    private static final Logger LOGGER = Logger.getLogger(WeatherDataQueue.class.getName());

    @Resource(lookup = "jms/activeMqConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/WeatherData")
    private Queue queue;

    public void send(JsonObject weatherData) {
        try (Connection connection = connectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);
            producer.setTimeToLive(TimeUnit.SECONDS.toMillis(30));

            StringWriter payload = new StringWriter();
            JsonWriter jsonWriter = Json.createWriter(payload);
            jsonWriter.writeObject(weatherData);

            TextMessage textMessage = session.createTextMessage(payload.toString());
            textMessage.setJMSType("WeatherData");
            textMessage.setStringProperty("contentType", "application/vnd.weather.v1+json");

            producer.send(textMessage);
        } catch (JMSException e) {
            LOGGER.log(Level.WARNING, "Could not send JMS message.", e);
        }
    }
}
