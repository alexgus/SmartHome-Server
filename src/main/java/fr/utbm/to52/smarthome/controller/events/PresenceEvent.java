/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.services.com.MQTT;

/**
 * @author Alexandre Guyon
 *
 */
public class PresenceEvent extends AbstractEvent {

	private MQTT connection;
	
	/**
	 * @param s Couchdb client
	 * @param c The MQTT connection to use
	 */
	public PresenceEvent(CouchDbClient s, MQTT c) {
		super(s);
		this.connection = c;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.controller.events.AbstractEvent#inform(java.lang.Object)
	 */
	@Override
	public void inform(Object o) {
		this.registerEvent(getClass(), o);
		
		this.connection.publish("/light", "0");
	}

}
