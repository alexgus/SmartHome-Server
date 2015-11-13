/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.json.JSONObject;
import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.controller.Controller;
import fr.utbm.to52.smarthome.model.logbook.LogBook;
import fr.utbm.to52.smarthome.repository.DAO;

/**
 * @author Alexandre Guyon
 *
 */
public class GetLogBookEvent extends AbstractDAOComEvent<LogBook> {

	/**
	 * @param s {@link AbstractEvent}
	 * @param d {@link AbstractDAOEvent}
	 * @param mqtt {@link AbstractDAOComEvent}
	 */
	public GetLogBookEvent(CouchDbClient s, DAO<LogBook> d, fr.utbm.to52.smarthome.services.com.MQTT mqtt) {
		super(s,d,mqtt);
	}

	@Override
	protected void informCmd(JSONObject data) {
		String s = "{}";
		if(data.length() == 0)
			s = this.dao.getRawData();
		else // Launch correct methods from DAO
			s = this.dao.getRawData(data);
		
		this.publishRawData(Controller.getInstance().getConfig().getCommandGetLogBook(), s);
	}

}