package de.qaware.oss.cloud.source.database;

import javax.annotation.Resource;
import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
@Named
public class DatasourceItemReader extends AbstractItemReader {

    private static final Logger LOGGER = Logger.getLogger(DatasourceItemProcessor.class.getName());

    @Inject
    private JobContext jobContext;

    @Inject
    @BatchProperty
    private String query;

    @Resource(lookup = "jdbc/VehicleDb")
    private DataSource vehicleDb;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        LOGGER.log(Level.INFO, "Open datasource reader.");

        connection = vehicleDb.getConnection();
        connection.setAutoCommit(false);

        statement = connection.createStatement();
        statement.setFetchSize(5);

        resultSet = statement.executeQuery(query);
    }

    @Override
    public void close() throws Exception {
        LOGGER.log(Level.INFO, "Close datasource reader.");
        resultSet.close();
        statement.close();
        connection.close();
    }

    @Override
    public Object readItem() throws Exception {
        LOGGER.log(Level.INFO, "Read datasource item.");
        if (resultSet.next()) {
            String vin = resultSet.getString("vin");
            return new DatasourceVehicle(vin);
        } else {
            return null;
        }
    }
}
