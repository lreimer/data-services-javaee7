package de.qaware.oss.cloud.source.database;

import javax.annotation.PostConstruct;
import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
@Named
public class DatasourceItemProcessor implements ItemProcessor {

    private static final Logger LOGGER = Logger.getLogger(DatasourceItemProcessor.class.getName());

    private Random random;

    @PostConstruct
    public void initialize() {
        random = new SecureRandom();
    }

    @Override
    public Object processItem(Object item) throws Exception {
        DatasourceVehicle vehicle = (DatasourceVehicle) item;
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("vin", vehicle.getVin())
                .add("latitude", random.nextDouble())
                .add("longitude", random.nextDouble())
                .build();

        LOGGER.log(Level.INFO, "Processed vehicle item {0}.", jsonObject);
        return jsonObject;

    }
}
