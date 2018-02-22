package de.qaware.oss.cloud.batch.csv;

import javax.batch.api.Batchlet;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
@Named("csvInfoBatchlet")
public class CsvInfoBatchlet implements Batchlet {

    private static final Logger LOGGER = Logger.getLogger(CsvInfoBatchlet.class.getName());

    @Inject
    private JobContext jobContext;

    @Inject
    private StepContext stepContext;

    @Override
    public String process() throws Exception {
        String inputFile = jobContext.getProperties().getProperty("input.file");

        LOGGER.log(Level.INFO, "Running step {0}.", stepContext.getStepName());
        LOGGER.log(Level.INFO, "Importing CSV from file {0}.", inputFile);

        return "COMPLETED";
    }

    @Override
    public void stop() throws Exception {
    }
}
