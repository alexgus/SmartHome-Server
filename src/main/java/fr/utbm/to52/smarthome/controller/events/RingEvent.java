/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.hibernate.Session;

import fr.utbm.to52.smarthome.controller.Controller;
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
	 * @param s Hibernate session
	 * @param c A valid MQTT connection
	 */
	public RingEvent(Session s, MQTT c) {
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
		
        this.connection.publish(Controller.getInstance().getConfig().getMQTTRingTopic(), 
        		Controller.getInstance().getConfig().getMQTTRingPayload());
	}
}
