/**
 * 
 */
package fr.utbm.to52.smarthome.services.com;

import org.eclipse.paho.client.mqttv3.MqttCallback;

import fr.utbm.to52.smarthome.services.AbstractService;

/**
 * @author Alexandre Guyon
 *
 */
public class MQTTService extends AbstractService {

	private MQTT mqtt;

	private MqttCallback cmdHandler;
	
	/**
	 * Constructor creating new MQTT service.
	 * @param c The {@link MqttCallback} for handling commands
	 */
	public MQTTService(MqttCallback c) {
		this.cmdHandler = c;
	}

	@Override
	public void start() {
		if(this.mqtt == null)
			this.mqtt = new MQTT(this.config.getMQTTID(), this.config.getMQTTServer(), this.config.getMQTTQOS(), this.cmdHandler);
		this.mqtt.connect();
		
		this.mqtt.subscribe(this.config.getBedTopic());
		this.mqtt.subscribe(this.config.getMotionTopic());
	}

	@Override
	public void stop() {
		this.mqtt.disconnect();
	}

	/**
	 * @return the mqtt
	 */
	public MQTT getMqtt() {
		return this.mqtt;
	}
	
}
