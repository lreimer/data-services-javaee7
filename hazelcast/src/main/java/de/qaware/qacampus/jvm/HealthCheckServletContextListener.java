package de.qaware.qacampus.jvm;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

/**
 * Metrics Health check listener.
 */
@WebListener
public class HealthCheckServletContextListener extends HealthCheckServlet.ContextListener {

    public static final HealthCheckRegistry HEALTH_CHECK_REGISTRY = new HealthCheckRegistry();

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);

        HEALTH_CHECK_REGISTRY.register("OK", new HealthCheck() {
            @Override
            protected Result check() throws Exception {
                return Result.healthy("Everything is OK.");
            }
        });
    }

    @Override
    protected HealthCheckRegistry getHealthCheckRegistry() {
        return HEALTH_CHECK_REGISTRY;
    }

}