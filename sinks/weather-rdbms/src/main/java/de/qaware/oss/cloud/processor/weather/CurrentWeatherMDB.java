package de.qaware.oss.cloud.processor.weather;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

@MessageDriven(name = "CurrentWeatherMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/CurrentWeather"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "CURRENT.WEATHER"),
        @ActivationConfigProperty(propertyName = "resourceAdapter", propertyValue = "activemq-rar"),
        @ActivationConfigProperty(propertyName = "messageSelector",
                propertyValue = "(JMSType = 'CurrentWeather') AND (contentType = 'application/vnd.weather.v1+json')")
})
public class CurrentWeatherMDB implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(CurrentWeatherMDB.class.getName());

    @Inject
    private CurrentWeatherRepo weatherRepo;

    @Override
    public void onMessage(Message message) {
        LOGGER.log(Level.INFO, "Received inbound message {0}.", message);

        String body = getBody(message);
        if (body != null) {
            CurrentWeather currentWeather = CurrentWeather.fromString(body);
            weatherRepo.save(currentWeather);
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
