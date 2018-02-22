package de.qaware.oss.cloud.batch.csv;

import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
@Named("csvItemReader")
public class CsvItemReader extends AbstractItemReader {

    private static final Logger LOGGER = Logger.getLogger(CsvItemProcessor.class.getName());

    @Inject
    private JobContext jobContext;

    private BufferedReader br;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        String inputFile = jobContext.getProperties().getProperty("input.file");
        br = Files.newBufferedReader(Paths.get(inputFile), StandardCharsets.UTF_8);
    }

    @Override
    public void close() throws Exception {
        LOGGER.log(Level.INFO, "Closing CSV reader.");
        br.close();
        br = null;
    }

    @Override
    public Object readItem() throws Exception {
        String item = br.readLine();
        LOGGER.log(Level.INFO, "Read CSV line time {0}.", item);
        return item;
    }
}
