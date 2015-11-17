/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.lightcouch.CouchDbClient;

/**
 * @author Alexandre Guyon
 *
 */
public class MotionSensor extends AbstractEvent{
	
	private Event toInform;
	
	/**
	 * Create Abort event for the clock
	 * @param s Couchdb client
	 * @param e Event to trigger
	 */
	public MotionSensor(CouchDbClient s, Event e) {
		super(s);
		this.toInform = e;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.controller.events.AbstractEvent#inform(java.lang.Object)
	 */
	@Override
	public void inform(Object o) {
		this.registerEvent(getClass(), o);
		
		this.toInform.inform(null);
	}

}
