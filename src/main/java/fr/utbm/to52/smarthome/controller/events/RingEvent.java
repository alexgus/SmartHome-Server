/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.controller.Conf;
import fr.utbm.to52.smarthome.controller.events.core.AbstractEvent;
import fr.utbm.to52.smarthome.services.com.MQTT;

/**
 * Send MQTT ring message
 * 
 * @author Alexandre Guyon
 *
 */
public class RingEvent extends AbstractEvent{

	private MQTT connection;  
	
	/**
	 * Default constructor. Initialize with valid MQTT connection
	 * @param s Couchdb session
	 * @param c A valid MQTT connection
	 */
	public RingEvent(CouchDbClient s, MQTT c) {
		super(s);
		this.connection = c;
	}
	
	/**
	 * This event send MQTT message to the ring via MQTT
	 * with values in the Conf Object of the main controller.
	 */
	@Override
	public void inform(Object o) {
		this.registerEvent(getClass(), o);
		
        this.connection.publish(Conf.getInstance().getMQTTRingTopic(), 
        		Conf.getInstance().getMQTTRingPayload());
	}
}
