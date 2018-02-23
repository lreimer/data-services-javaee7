package de.qaware.oss.cloud.source.csv;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class CsvBatchImportSchedule {

    private static final Logger LOGGER = Logger.getLogger(CsvBatchImportSchedule.class.getName());

    @Schedule(minute = "*/1", hour = "*", persistent = false)
    public void processFiles() {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        long executionId = jobOperator.start("csv-batch-job", null);
        LOGGER.log(Level.INFO, "Start CSV import job {0}.", executionId);
    }
}
