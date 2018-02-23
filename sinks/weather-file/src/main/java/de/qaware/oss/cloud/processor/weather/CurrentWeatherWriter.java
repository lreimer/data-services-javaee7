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
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class CurrentWeatherWriter {

    private static final Logger LOGGER = Logger.getLogger(CurrentWeatherWriter.class.getName());

    private Path currentWeatherCsv;

    @PostConstruct
    public void initialize() {
        currentWeatherCsv = Paths.get("/tmp/current-weather.csv");
        createAndWriteCsvHeader();
    }

    private void createAndWriteCsvHeader() {
        if (Files.exists(currentWeatherCsv)) {
            return;
        }

        final String header = "local_date_time;city;weather";
        try {
            currentWeatherCsv = Files.write(currentWeatherCsv, Collections.singleton(header), StandardOpenOption.CREATE);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Unable to create and write file.", e);
        }
    }

    public void write(JsonObject jsonObject) throws IOException {
        String city = jsonObject.getString("city");
        String weather = jsonObject.getString("weather");
        String line = String.format("%s;%s;%s", LocalDateTime.now(), city, weather);
        currentWeatherCsv = Files.write(currentWeatherCsv, Collections.singleton(line), StandardOpenOption.APPEND);
    }

    public Path getCurrentWeatherCsv() {
        return currentWeatherCsv;
    }
}
