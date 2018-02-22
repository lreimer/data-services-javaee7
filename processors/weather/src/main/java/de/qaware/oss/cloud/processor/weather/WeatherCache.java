package de.qaware.oss.cloud.processor.weather;

import javax.cache.Cache;
import javax.cache.annotation.CacheDefaults;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@ApplicationScoped
@CacheDefaults(cacheName = "weatherCache")
public class WeatherCache {

    @Inject
    private Cache<String, String> weatherCache;

    @Inject
    private Event<CurrentWeatherEvent> weatherEvent;

    public void put(String city, String weather) {
        weatherCache.put(city, weather);
        weatherEvent.fire(new CurrentWeatherEvent(city, weather));
    }

    public String get(String city) {
        return weatherCache.get(city);
    }
}
