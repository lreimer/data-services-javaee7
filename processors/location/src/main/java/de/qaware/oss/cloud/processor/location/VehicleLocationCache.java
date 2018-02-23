package de.qaware.oss.cloud.processor.location;

import javax.cache.Cache;
import javax.cache.annotation.CacheDefaults;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@CacheDefaults(cacheName = "vehicleLocation")
public class VehicleLocationCache {

    private static final Logger LOGGER = Logger.getLogger(VehicleLocationCache.class.getName());

    @Inject
    private Cache<String, String> vehicleLocationCache;

    public void subscribe(@Observes VehicleLocationEvent event) {
        put(event);
    }

    void put(VehicleLocationEvent event) {
        String vin = event.getVin();
        String location = event.asLocation();

        LOGGER.log(Level.INFO, "Current location for VIN {0} is {1}.", new Object[]{vin, location});
        vehicleLocationCache.put(vin, location);
    }

    public JsonArray getLocation(String vin) {
        String location = vehicleLocationCache.get(vin);
        LOGGER.log(Level.INFO, "Current location for VIN {0} is {1}.", new Object[]{vin, location});
        return Json.createReader(new StringReader(location)).readArray();
    }

    public void clear() {
        vehicleLocationCache.clear();
    }
}
