package de.qaware.oss.cloud.processor.weather;

import javax.cache.Cache;
import javax.cache.annotation.CacheDefaults;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@CacheDefaults(cacheName = "weatherCache")
public class WeatherCache {

    private static final Logger LOGGER = Logger.getLogger(WeatherCache.class.getName());

    @Inject
    private Cache<String, String> weatherCache;

    @Inject
    private Event<CurrentWeatherEvent> weatherEvent;

    public void put(String city, String weather) {
        LOGGER.log(Level.INFO, "Set current weather in {0} to {1}.", new Object[]{city, weather});
        weatherCache.put(city, weather);
        weatherEvent.fire(new CurrentWeatherEvent(city, weather));
    }

    public String get(String city) {
        String weather = weatherCache.get(city);
        LOGGER.log(Level.INFO, "Current weather in {0} is {1}.", new Object[]{city, weather});
        return weather;
    }
}
