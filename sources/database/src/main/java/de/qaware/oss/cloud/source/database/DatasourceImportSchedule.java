package de.qaware.oss.cloud.source.database;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class DatasourceImportSchedule {

    private static final Logger LOGGER = Logger.getLogger(DatasourceImportSchedule.class.getName());

    @Schedule(minute = "*/1", hour = "*", persistent = false)
    public void processFiles() {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        long executionId = jobOperator.start("jdbc-batch-job", null);
        LOGGER.log(Level.INFO, "Start JDBC datasource import job {0}.", executionId);
    }
}
