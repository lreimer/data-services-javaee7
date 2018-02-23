package de.qaware.oss.cloud.processor.location;

import javax.json.*;
import java.io.StringReader;
import java.io.StringWriter;

public class VehicleLocationEvent {

    private final String vin;
    private final double latitude;
    private final double longitude;

    private VehicleLocationEvent(String vin, double latitude, double longitude) {
        this.vin = vin;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getVin() {
        return vin;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String asLocation() {
        StringWriter writer = new StringWriter();

        JsonArray location = Json.createArrayBuilder().add(latitude).add(longitude).build();
        JsonWriter jsonWriter = Json.createWriter(writer);
        jsonWriter.writeArray(location);
        jsonWriter.close();

        return writer.toString();
    }

    public static VehicleLocationEvent fromJson(String body) {
        JsonReader reader = Json.createReader(new StringReader(body));
        JsonObject jsonObject = reader.readObject();
        return new VehicleLocationEvent(
                jsonObject.getString("vin"),
                getNumber(jsonObject, "latitude"),
                getNumber(jsonObject, "longitude"));
    }

    private static double getNumber(JsonObject jsonObject, String name) {
        JsonValue jsonValue = jsonObject.get(name);
        switch (jsonValue.getValueType()) {
            case NUMBER:
                return ((JsonNumber) jsonValue).doubleValue();
            default:
                throw new IllegalArgumentException();
        }
    }
}
