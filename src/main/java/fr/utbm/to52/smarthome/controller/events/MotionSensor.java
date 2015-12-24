/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.controller.events.core.AbstractEvent;

/**
 * @author Alexandre Guyon
 *
 */
public class MotionSensor extends AbstractEvent{
	
	/**
	 * Create Abort event for the clock
	 * @param s Couchdb client
	 */
	public MotionSensor(CouchDbClient s) {
		super(s);
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.controller.events.AbstractEvent#inform(java.lang.Object)
	 */
	@Override
	public void inform(Object o) {
		this.registerEvent(getClass(), o);
	}

}
