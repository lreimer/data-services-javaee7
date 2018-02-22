package de.qaware.oss.cloud.source.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Read weather data from OpenWeatherMap API.
 * <p>
 * http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22
 */
@ApplicationScoped
public class OpenWeatherMapClient {

    @Inject
    @Named("weather")
    private WebTarget webTarget;

    public JsonObject getWeatherData(String city) {
        return webTarget
                .queryParam("q", city)
                .queryParam("appid", "b6907d289e10d714a6e88b30761fae22")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(JsonObject.class);
    }
}
