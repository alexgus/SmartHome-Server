/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.controller.Controller;

/**
 * @author Alexandre Guyon
 *
 */
public class QuitEvent extends AbstractEvent {

	private Controller controller;
	
	/**
	 * Create QuitEvent
	 * @param c Controller to stop
	 * @param s {@link AbstractEvent}
	 */
	public QuitEvent(Controller c, CouchDbClient s) {
		super(s);
		this.controller = c;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.controller.events.AbstractEvent#inform(java.lang.Object)
	 */
	@Override
	public void inform(Object o) {
		this.registerEvent(getClass(), o);
		
		this.controller.stop();
	}

}
