package de.qaware.oss.cloud.source.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class MqttSourceMDBTest {

    @Test
    public void onMQTTMessage() {
        MqttSourceMDB mdb = new MqttSourceMDB();
        mdb.vehicleLocation = mock(VehicleLocationTopic.class);

        mdb.onMQTTMessage("test/topic", new MqttMessage("{ \"aKey\":\"aValue\" }".getBytes()));
    }
}