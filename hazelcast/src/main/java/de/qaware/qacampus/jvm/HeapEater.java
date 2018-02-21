package de.qaware.qacampus.jvm;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static java.lang.String.format;

/**
 * Do the heap consumption.
 */
@ApplicationScoped
public class HeapEater {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    @Resource
    private ManagedScheduledExecutorService executorService;

    private final Map<Long, byte[]> cache = new HashMap<>();
    private final AtomicBoolean stop = new AtomicBoolean(false);

    private final int sizeInMB;
    private final long delayInSeconds;

    public HeapEater() {
        this.sizeInMB = 32;
        this.delayInSeconds = 3;
    }

    public String eat() {
        stop.set(false);

        executorService.scheduleWithFixedDelay(() -> {
            if (stop.get()) {
                throw new CancellationException();
            }

            try {
                // produce some more garbage objects
                Map<Integer, String> uuids = new HashMap<>();
                IntStream.range(0, 5000).forEach(i -> uuids.put(i, UUID.randomUUID().toString()));

                // claim memory and cache it
                LOGGER.log(Level.INFO, "Allocating {0}MB memory.", sizeInMB);
                cache.put(System.currentTimeMillis(), new byte[1024 * 1024 * sizeInMB]);
            } catch (Throwable t) {
                LOGGER.log(Level.SEVERE, "Unable to allocate memory.", t);
            }

        }, 0, delayInSeconds, TimeUnit.SECONDS);

        return "Start eating memory.";
    }

    public String stop() {
        stop.set(true);
        return "Stop eating memory.";
    }

    public String free() {
        cache.clear();
        return "Cleanup memory.";
    }

    public String gc() {
        System.gc();
        return "Performing GC.";
    }

    public String size() {
        return format("I have %sMB memory in my belly.", cache.size() * 32);
    }
}
