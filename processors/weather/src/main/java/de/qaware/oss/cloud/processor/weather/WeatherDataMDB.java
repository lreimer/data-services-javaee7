package de.qaware.oss.cloud.processor.weather;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

@MessageDriven(name = "CurrentWeatherMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/WeatherData"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "WEATHER.DATA"),
        @ActivationConfigProperty(propertyName = "resourceAdapter", propertyValue = "activemq-rar"),
        @ActivationConfigProperty(propertyName = "messageSelector",
                propertyValue = "(JMSType = 'WeatherData') AND (contentType = 'application/vnd.weather.v1+json')")
})
public class WeatherDataMDB implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(WeatherDataMDB.class.getName());

    @Inject
    private WeatherProcessor weatherProcessor;

    @Override
    public void onMessage(Message message) {
        LOGGER.log(Level.INFO, "Received inbound message {0}.", message);

        String body = getBody(message);
        if (body != null) {
            JsonReader reader = Json.createReader(new StringReader(body));
            weatherProcessor.process(reader.readObject());
        }
    }

    private String getBody(Message message) {
        String body = null;
        try {
            if (message instanceof TextMessage) {
                body = ((TextMessage) message).getText();
            }
        } catch (JMSException e) {
            LOGGER.log(Level.WARNING, "Could not get message body.", e);
        }
        return body;
    }
}
