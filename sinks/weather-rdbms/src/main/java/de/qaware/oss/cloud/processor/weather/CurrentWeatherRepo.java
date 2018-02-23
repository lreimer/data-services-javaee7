package de.qaware.oss.cloud.processor.weather;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Transactional
public class CurrentWeatherRepo {

    private static final Logger LOGGER = Logger.getLogger(CurrentWeatherRepo.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    public void save(CurrentWeather currentWeather) {
        LOGGER.log(Level.INFO, "Saving {0}.", currentWeather);
        entityManager.merge(currentWeather);
    }
}
