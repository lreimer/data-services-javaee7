package de.qaware.oss.cloud.batch.csv;

import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
@Named
public class CsvItemProcessor implements ItemProcessor {

    private static final Logger LOGGER = Logger.getLogger(CsvItemProcessor.class.getName());

    @Override
    public Object processItem(Object item) throws Exception {
        String line = (String) item;
        String[] values = line.split(";");
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("vin", values[0])
                .add("latitude", Double.valueOf(values[1]))
                .add("longitude", Double.valueOf(values[2]))
                .build();
        LOGGER.log(Level.INFO, "Processed item {0}.", jsonObject);
        return jsonObject;

    }
}
