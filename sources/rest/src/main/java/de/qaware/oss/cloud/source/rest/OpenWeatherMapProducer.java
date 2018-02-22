package de.qaware.oss.cloud.source.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * Factory for JAX-RS client and weather web target instances.
 */
@ApplicationScoped
public class OpenWeatherMapProducer {

    @Produces
    @Default
    @ApplicationScoped
    public Client client() {
        return ClientBuilder.newClient();
    }

    @Produces
    @Default
    @Named("weather")
    @ApplicationScoped
    public WebTarget weatherWebTarget(Client client) {
        return client.target("http://samples.openweathermap.org").path("/data/2.5/weather");
    }

    public void close(@Disposes Client client) {
        client.close();
    }
}
