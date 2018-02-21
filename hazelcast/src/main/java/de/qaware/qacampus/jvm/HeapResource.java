package de.qaware.qacampus.jvm;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

/**
 * The REST resource to do stuff with the heap.
 */
@Path("/heap")
@ApplicationScoped
@Produces(MediaType.TEXT_PLAIN)
public class HeapResource {

    @Inject
    private HeapEater eater;

    /**
     * Do stuffwith the heap.
     *
     * @param command eat, stop, puke, free, gc
     * @return the response
     */
    @GET
    public Response doHeap(@QueryParam("command") @DefaultValue("") String command) {
        String response;
        switch (Objects.toString(command, "")) {
            case "eat":
                response = eater.eat();
                break;
            case "stop":
                response = eater.stop();
                break;
            case "puke":
                // same as next
            case "free":
                response = eater.free();
                break;
            case "gc":
                response = eater.gc();
                break;
            default:
                response = eater.size();
                break;
        }

        return Response.ok(response).build();
    }

}
