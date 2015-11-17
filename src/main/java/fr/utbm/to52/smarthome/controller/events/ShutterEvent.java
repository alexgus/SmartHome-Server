/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.controller.Conf;
import fr.utbm.to52.smarthome.services.com.MQTT;

/**
 * @author Alexandre Guyon
 *
 */
public class ShutterEvent extends AbstractEvent {
	
	private MQTT connection;

	/**
	 * Create shutter event for open or close the blind 
	 * @param s couchdb session
	 * @param c The MQTT connection to use
	 */
	public ShutterEvent(CouchDbClient s, MQTT c) {
		super(s);
		this.connection = c;
	}

	@Override
	public void inform(Object o) {
		this.registerEvent(getClass(), o);
		// TODO open or close with o
		this.connection.publish(Conf.getInstance().getMQTTShutterTopic(), Conf.getInstance().getCommandShutter());
	}



}
