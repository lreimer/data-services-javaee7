package de.qaware.oss.cloud.processor.weather;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Collections;

@ApplicationScoped
public class CurrentWeatherWriter {

    private Path currentWeatherCsv;

    @PostConstruct
    public void initialize() throws IOException {
        currentWeatherCsv = Paths.get("/data/current-weather.csv");
        if (!Files.exists(currentWeatherCsv)) {
            final String header = "local_date_time;city;weather";
            currentWeatherCsv = Files.write(currentWeatherCsv, Collections.singleton(header), StandardOpenOption.CREATE);
        }
    }

    public void write(JsonObject jsonObject) throws IOException {
        String city = jsonObject.getString("city");
        String weather = jsonObject.getString("weather");
        String line = String.format("%s;%s;%s", LocalDateTime.now(), city, weather);
        currentWeatherCsv = Files.write(currentWeatherCsv, Collections.singleton(line), StandardOpenOption.APPEND);
    }
}
