package de.qaware.oss.cloud.source.rest;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.JsonObject;
import java.util.Arrays;
import java.util.List;
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

    @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
    public void currentWeather() {
        try {
            cities().forEach(city -> {
                LOGGER.log(Level.INFO, "Getting current weather.");
                JsonObject weatherData = weatherMapClient.getWeatherData(city);

                LOGGER.log(Level.INFO, "Send weather data {0}.", weatherData);
                weatherDataQueue.send(weatherData);
            });
        } catch (RuntimeException e) {
            LOGGER.log(Level.WARNING, "Error during scheduled execution.", e);
        }
    }

    private List<String> cities() {
        return Arrays.asList("London,uk", "Heidelberg,DE", "Rosenheim,DE");
    }
}
