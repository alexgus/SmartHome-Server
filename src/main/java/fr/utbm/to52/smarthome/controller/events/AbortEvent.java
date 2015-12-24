/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.controller.Conf;
import fr.utbm.to52.smarthome.controller.events.core.AbstractEvent;
import fr.utbm.to52.smarthome.services.com.MQTT;

/**
 * @author Alexandre Guyon
 *
 */
public class AbortEvent extends AbstractEvent {

	private MQTT connection;
	
	/**
	 * Create Abort event for the clock
	 * @param s Couchdb client
	 * @param con MQTT connection
	 */
	public AbortEvent(CouchDbClient s, MQTT con) {
		super(s);
		this.connection = con;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.controller.events.AbstractEvent#inform(java.lang.Object)
	 */
	@Override
	public void inform(Object o) {
		this.registerEvent(getClass(), o);
		
		this.connection.publish(Conf.getInstance().getMQTTRingTopic(), 
        		Conf.getInstance().getMQTTAbortPayload());
	}

}
