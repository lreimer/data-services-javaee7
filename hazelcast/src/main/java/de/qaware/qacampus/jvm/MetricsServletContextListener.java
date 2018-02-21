package de.qaware.qacampus.jvm;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.ClassLoadingGaugeSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.codahale.metrics.servlets.MetricsServlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

/**
 * Metrics Servlet listener.
 */
@WebListener
public class MetricsServletContextListener extends MetricsServlet.ContextListener {

    public static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);

        METRIC_REGISTRY.registerAll(new GarbageCollectorMetricSet());
        METRIC_REGISTRY.registerAll(new MemoryUsageGaugeSet());
        METRIC_REGISTRY.registerAll(new ThreadStatesGaugeSet());
        METRIC_REGISTRY.registerAll(new ClassLoadingGaugeSet());
    }

    @Override
    protected MetricRegistry getMetricRegistry() {
        return METRIC_REGISTRY;
    }
}