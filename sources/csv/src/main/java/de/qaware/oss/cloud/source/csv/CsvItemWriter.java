package de.qaware.oss.cloud.source.csv;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
@Named
public class CsvItemWriter extends AbstractItemWriter {
    private static final Logger LOGGER = Logger.getLogger(CsvItemWriter.class.getName());

    @Inject
    private VehicleLocationTopic vehicleLocation;

    @Override
    public void writeItems(List<Object> items) throws Exception {
        LOGGER.log(Level.INFO, "Writing {0} items to topic.", items.size());
        items.forEach(item -> {
            LOGGER.log(Level.INFO, "Publish vehicle location {0}.", item);
            vehicleLocation.publish((JsonObject) item);
        });
    }
}
