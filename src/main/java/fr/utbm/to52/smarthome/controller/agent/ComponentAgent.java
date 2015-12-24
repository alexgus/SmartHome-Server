/**
 * 
 */
package fr.utbm.to52.smarthome.controller.agent;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.controller.Conf;
import fr.utbm.to52.smarthome.controller.Message;
import fr.utbm.to52.smarthome.services.com.MQTTService;
import fr.utbm.to52.smarthome.services.couchdb.CouchdbService;
import madkit.kernel.Agent;

/**
 * @author Alexandre Guyon
 *
 */
public abstract class ComponentAgent extends Agent implements MqttCallback{
	
	private CouchDbClient couch;
	
	protected MQTTService mqtt = new MQTTService(this);
	
	/**
	 * Start services
	 */
	public ComponentAgent() {
		this.mqtt.setUp(Conf.getInstance());
		this.mqtt.start();
		CouchdbService c = new CouchdbService();
		c.setUp(Conf.getInstance());
		c.start();
		this.couch = c.getSession();
	}
	
	/**
	 * Inform about an event
	 * @param m Message received
	 */
	protected abstract void inform(Message m);
	
	@Override
	public void connectionLost(Throwable arg0) {
		this.logger.info("MQTT connection lost");
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// Nop
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		Message m = new Message(arg0.toString(), arg1.toString());
		this.couch.save(m);
		this.inform(m);
	}
	
}
