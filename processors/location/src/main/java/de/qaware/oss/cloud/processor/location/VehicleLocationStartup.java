package de.qaware.oss.cloud.processor.location;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class VehicleLocationStartup {
    private static final Logger LOGGER = Logger.getLogger(VehicleLocationStartup.class.getName());

    @Inject
    private VehicleLocationCache cache;

    @PostConstruct
    public void startup() {
        cache.clear();
        LOGGER.log(Level.INFO, "Clearing vehicle location cache.");
    }
}
