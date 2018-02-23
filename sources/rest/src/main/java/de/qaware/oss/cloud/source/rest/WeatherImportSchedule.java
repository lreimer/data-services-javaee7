package de.qaware.oss.cloud.source.rest;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.JsonObject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class WeatherImportSchedule {

    private static final Logger LOGGER = Logger.getLogger(WeatherImportSchedule.class.getName());

    @Inject
    private OpenWeatherMapClient weatherMapClient;

    @Inject
    private WeatherDataQueue weatherDataQueue;

    @Schedule(minute = "*/1", hour = "*", persistent = false)
    public void currentWeather() {
        try {
            LOGGER.log(Level.INFO, "Getting current weather.");
            JsonObject weatherData = weatherMapClient.getWeatherData("London,uk");

            LOGGER.log(Level.INFO, "Send weather data {0}.", weatherData);
            weatherDataQueue.send(weatherData);
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Error during scheduled execution.", e);
        }
    }
}
