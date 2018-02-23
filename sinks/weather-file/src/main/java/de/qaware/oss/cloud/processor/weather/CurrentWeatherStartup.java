package de.qaware.oss.cloud.processor.weather;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class CurrentWeatherStartup {

    private static final Logger LOGGER = Logger.getLogger(CurrentWeatherStartup.class.getName());

    @Inject
    private CurrentWeatherWriter writer;

    @PostConstruct
    public void startup() {
        LOGGER.log(Level.INFO, "Writing current weather to {0}", writer.getCurrentWeatherCsv());
    }
}
