/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.hibernate.ogm.OgmSession;

/**
 * @author Alexandre Guyon
 *
 */
public class NoSuchCommand extends AbstractEvent {

	/**
	 * @param s {@link AbstractEvent}
	 */
	public NoSuchCommand(OgmSession s) {
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
