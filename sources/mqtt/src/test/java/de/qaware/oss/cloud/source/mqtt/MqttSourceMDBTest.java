package de.qaware.oss.cloud.source.mqtt;

import de.qaware.oss.cloud.source.mqtt.MqttSourceMDB;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;

public class MqttSourceMDBTest {

    @Test
    public void onMQTTMessage() {
        MqttSourceMDB mdb = new MqttSourceMDB();
        mdb.onMQTTMessage("test/topic", new MqttMessage("{ \"aKey\":\"aValue\" }".getBytes()));
    }
}