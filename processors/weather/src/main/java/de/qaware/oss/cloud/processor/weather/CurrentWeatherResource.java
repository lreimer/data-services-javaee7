package de.qaware.oss.cloud.processor.weather;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@ApplicationScoped
@Path("weather")
public class CurrentWeatherResource {

    @Inject
    private WeatherCache weatherCache;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{city}")
    public Response current(@PathParam("city") String city) {
        String weather = Optional.ofNullable(weatherCache.get(city)).orElseThrow(NotFoundException::new);
        return Response.ok(weather).build();
    }
}
