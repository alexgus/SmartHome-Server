/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.hibernate.ogm.OgmSession;
import org.json.JSONObject;

import fr.utbm.to52.smarthome.repository.DAO;

/**
 * @author Alexandre Guyon
 * @param <N> To insert
 *
 */
public abstract class AbstractDAOEvent<N> extends AbstractEvent {

	/**
	 * Using this DAO;
	 */
	protected DAO<N> dao;

	/**
	 * @param s {@link AbstractEvent}
	 * @param d Corresponding DAO
	 */
	public AbstractDAOEvent(OgmSession s, DAO<N> d) {
		super(s);
		this.dao = d;
	}

	@Override
	public void inform(Object o) {
		this.registerEvent(getClass(), o);

		String cmdline = (String) o;
		String json = cmdline.substring(cmdline.indexOf(" "));

		this.informCmd(new JSONObject(json));
	}

	/**
	 * Inform data command
	 * @param data The data to inform
	 */
	protected abstract void informCmd(JSONObject data);

}
