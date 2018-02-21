package de.qaware.qacampus.jvm;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Collections.singletonMap;

/**
 * Hello World REST resource.
 */
@Path("/hello")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {
    @GET
    public Response index(@QueryParam("name") String q) {
        Optional<String> name = Optional.ofNullable(q);
        String message = "Hello " + name.orElse("World") + " @" + LocalDateTime.now();
        return Response.ok(singletonMap("message", message)).build();
    }
}
