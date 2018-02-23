package de.qaware.oss.cloud.processor.weather;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.StringReader;

@Entity
@Table(name = "current_weather")
public class CurrentWeather {

    @Id
    @Column(name = "city", unique = true, nullable = false)
    private String city;

    @Column(name = "weather", nullable = false)
    private String weather;

    public CurrentWeather() {
    }

    public CurrentWeather(String city, String weather) {
        this.city = city;
        this.weather = weather;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public static CurrentWeather fromString(String json) {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject jsonObject = reader.readObject();

        String city = jsonObject.getString("city");
        String weather = jsonObject.getString("weather");

        return new CurrentWeather(city, weather);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CurrentWeather{");
        sb.append("city='").append(city).append('\'');
        sb.append(", weather='").append(weather).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
