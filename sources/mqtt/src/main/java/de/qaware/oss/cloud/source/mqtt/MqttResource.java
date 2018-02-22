package de.qaware.oss.cloud.source.mqtt;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/mqtt")
public class MqttResource {

    @Inject
    private MqttMessenger messenger;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response publish(String body) {
        messenger.publish(body.getBytes());
        return Response.noContent().build();
    }
}
