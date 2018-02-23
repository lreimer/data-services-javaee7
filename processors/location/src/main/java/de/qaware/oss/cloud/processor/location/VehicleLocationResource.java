package de.qaware.oss.cloud.processor.location;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@ApplicationScoped
@Path("location")
public class VehicleLocationResource {

    @Inject
    private VehicleLocationCache locationCache;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{vin}")
    public Response current(@PathParam("vin") String vin) {
        JsonArray location = Optional.ofNullable(locationCache.getLocation(vin)).orElseThrow(NotFoundException::new);
        return Response.ok(location).build();
    }
}
