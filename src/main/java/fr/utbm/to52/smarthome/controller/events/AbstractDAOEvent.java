/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.json.JSONObject;
import org.lightcouch.CouchDbClient;

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
	public AbstractDAOEvent(CouchDbClient s, DAO<N> d) {
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
