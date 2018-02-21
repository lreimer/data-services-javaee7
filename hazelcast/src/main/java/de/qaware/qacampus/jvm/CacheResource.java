package de.qaware.qacampus.jvm;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.CompleteConfiguration;
import javax.cache.configuration.MutableConfiguration;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * The REST resource to do stuff with the heap.
 */
@Path("/hazelcast")
@Produces(MediaType.APPLICATION_JSON)
public class CacheResource {

    @Inject
    private CacheManager cacheManager;

    @GET
    @Path("/{name}/{key}")
    public JsonObject getCache(@PathParam("name") String name, @PathParam("key") String key) {
        Cache<String, String> cache = getOrCreateCache(name);

        String value = Optional.ofNullable(cache.get(key))
                .orElseThrow(() -> new NotFoundException("Cache does not "));

        return Json.createObjectBuilder()
                .add("key", key)
                .add("value", value).build();
    }

    @POST
    @Path("/{name}/{key}")
    @Consumes({MediaType.TEXT_PLAIN})
    public Response postCache(@PathParam("name") String name, @PathParam("key") String key, String value) {
        Cache<String, String> cache = getOrCreateCache(name);
        cache.put(key, value);
        return Response.noContent().build();
    }

    private Cache<String, String> getOrCreateCache(String name) {
        Cache<String, String> cache = cacheManager.getCache(name, String.class, String.class);
        if (cache == null) {
            CompleteConfiguration<String, String> config = new MutableConfiguration<String, String>().setTypes(String.class, String.class);
            cache = cacheManager.createCache(name, config);
        }
        return cache;
    }
}
