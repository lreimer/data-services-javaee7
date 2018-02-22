package de.qaware.oss.cloud.source.csv;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/import")
public class CsvBatchImportResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject start() {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        long executionId = jobOperator.start("csv-batch-job", null);

        return Json.createObjectBuilder().add("executionId", executionId).build();
    }
}
