package de.qaware.qacampus.jvm;

import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * The REST API application.
 */
@ApplicationPath("/api/")
public class RestAPI extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        classes.add(JacksonFeature.class);
        classes.add(HelloResource.class);
        classes.add(HeapResource.class);

        return classes;
    }
}
