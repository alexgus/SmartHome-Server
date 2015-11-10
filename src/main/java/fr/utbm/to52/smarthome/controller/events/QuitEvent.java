/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.hibernate.Session;

import fr.utbm.to52.smarthome.controller.Controller;

/**
 * @author Alexandre Guyon
 *
 */
public class QuitEvent extends AbstractEvent {

	/**
	 * @param s {@link AbstractEvent}
	 */
	public QuitEvent(Session s) {
		super(s);
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.controller.events.AbstractEvent#inform(java.lang.Object)
	 */
	@Override
	public void inform(Object o) {
		this.registerEvent(getClass(), o);
		
		Controller.getInstance().stop();
	}

}
