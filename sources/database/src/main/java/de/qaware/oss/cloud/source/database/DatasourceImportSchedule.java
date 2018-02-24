package de.qaware.oss.cloud.source.database;

import javax.annotation.PostConstruct;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class DatasourceImportSchedule {

    private static final Logger LOGGER = Logger.getLogger(DatasourceImportSchedule.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    private void initialize() {
        LOGGER.log(Level.INFO, "JPA entity manager for Vehicles is open={0}.", entityManager.isOpen());
    }

    @Schedule(minute = "*/1", hour = "*", persistent = false)
    public void processFiles() {

        JobOperator jobOperator = BatchRuntime.getJobOperator();
        long executionId = jobOperator.start("jdbc-batch-job", null);

        LOGGER.log(Level.INFO, "Started JDBC datasource import job {0}.", executionId);
    }
}
