/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.json.JSONObject;
import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.controller.Controller;
import fr.utbm.to52.smarthome.repository.DAO;
import fr.utbm.to52.smarthome.services.com.MQTT;

/**
 * @author Alexandre Guyon
 * @param <N> {@link AbstractDAOEvent}
 *
 */
public abstract class AbstractDAOComEvent<N> extends AbstractDAOEvent<N> {

	private MQTT mqtt;
	
	/**
	 * @param s {@link AbstractDAOEvent}
	 * @param d {@link AbstractDAOEvent}
	 * @param mqtt Mqtt connection for result
	 */
	public AbstractDAOComEvent(CouchDbClient s, DAO<N> d, MQTT mqtt) {
		super(s, d);
		this.mqtt = mqtt;
	}

	/**
	 * Publish response to MQTT
	 * @param topic Topic to send the answer
	 * @param data JSONobject to answer
	 */
	protected void publish(String topic, JSONObject data){
		this.mqtt.publish("/" + topic + "/" + Controller.getInstance().getConfig().getTopicAnswerSuffix(), data.toString());
	}
	
	/**
	 * Publish response to MQTT
	 * @param topic Topic to send the answer
	 * @param data String to send
	 */
	protected void publish(String topic, String data){
		this.mqtt.publish("/" + topic + "/" + Controller.getInstance().getConfig().getTopicAnswerSuffix(), data);
	}
	
}
