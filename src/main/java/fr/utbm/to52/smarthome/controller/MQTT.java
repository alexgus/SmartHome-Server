/**
 * 
 */
package fr.utbm.to52.smarthome.controller;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Handle the MQTT connection for this app
 * 
 * @author Alexandre Guyon
 *
 */
public class MQTT implements MqttCallback{
	
	private String id;
	
	private String broker;
	
	private int QOS;
	
	private MemoryPersistence persistence;
	
	private MqttClient client;
	
	private MqttConnectOptions connOpts;
	
	/**
	 * Initialize client with default option
	 * @param id Id to set to the client
	 * @param server Broker ==> tcp://hostname:port
	 * @param QOS Default QOS
	 */
	public MQTT(String id, String server, int QOS){
		this.id = id;
		this.broker = server;
		this.QOS = QOS;
		this.persistence = new MemoryPersistence();
		try {
			this.client = new MqttClient(this.broker, this.id, this.persistence);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		this.connOpts = new MqttConnectOptions();
        this.connOpts.setCleanSession(true);
	}
	
	/**
	 * Connect to the broker
	 */
	public void connect(){
		 try {
			this.client.connect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Disconnect from the broker
	 */
	public void disconnect(){
		try {
			this.client.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Disconnect then reconnect to the broker
	 */
	public void reconnect(){
		this.disconnect();
		this.reconnect();
	}
	
	/**
	 * Publish MQTT message on a topic
	 * @param topic The topic to post
	 * @param message The message to post
	 */
	public void publish(String topic, String message){
		MqttMessage m = new MqttMessage(message.getBytes());
        m.setQos(this.QOS);
        try {
			this.client.publish(topic, m);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable arg0) {
		this.reconnect();
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// Nothing
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		// TODO Configuration modifier
	}

}
