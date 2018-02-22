package de.qaware.oss.cloud.processor.weather;

import javax.json.Json;
import javax.json.JsonObject;

public class CurrentWeatherEvent {

    private final String city;
    private final String weather;

    public CurrentWeatherEvent(String city, String weather) {
        this.city = city;
        this.weather = weather;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("city", city)
                .add("weather", weather)
                .build();
    }
}
