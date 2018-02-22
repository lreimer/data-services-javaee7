package de.qaware.oss.cloud.processor.weather;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;

@ApplicationScoped
public class WeatherProcessor {

    @Inject
    private WeatherCache weatherCache;

    public void process(JsonObject weatherData) {
        String city = weatherData.getString("name");
        String weather = weatherData.getJsonArray("weather").getJsonObject(0).getString("main");
        weatherCache.put(city, weather);
    }
}
