/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import java.util.List;

import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.controller.events.core.AbstractEvent;
import fr.utbm.to52.smarthome.controller.events.core.Event;

/**
 * @author Alexandre Guyon
 *
 */
public class MotionSensor extends AbstractEvent{
	
	/**
	 * AbortEvent{0}, 
	 */
	private List<Event> toInform;
	
	/**
	 * Create Abort event for the clock
	 * @param s Couchdb client
	 * @param e Event to trigger
	 */
	public MotionSensor(CouchDbClient s, List<Event> e) {
		super(s);
		this.toInform = e;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.controller.events.AbstractEvent#inform(java.lang.Object)
	 */
	@Override
	public void inform(Object o) {
		this.registerEvent(getClass(), o);
		
		for (Event event : this.toInform) 
			event.inform(null);
	}

}
