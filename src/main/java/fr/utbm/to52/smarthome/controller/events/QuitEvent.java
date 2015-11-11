/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.hibernate.ogm.OgmSession;

import fr.utbm.to52.smarthome.controller.Controller;

/**
 * @author Alexandre Guyon
 *
 */
public class QuitEvent extends AbstractEvent {

	/**
	 * @param s {@link AbstractEvent}
	 */
	public QuitEvent(OgmSession s) {
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
