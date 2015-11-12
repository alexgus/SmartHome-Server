/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.json.JSONObject;
import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.controller.Controller;
import fr.utbm.to52.smarthome.model.note.Note;
import fr.utbm.to52.smarthome.repository.DAO;
import fr.utbm.to52.smarthome.services.com.MQTT;

/**
 * @author Alexandre Guyon
 *
 */
public class GetNoteEvent extends AbstractDAOComEvent<Note> {
	
	/**
	 * @param s {@link AbstractEvent}
	 * @param d {@link AbstractDAOEvent}
	 * @param mqtt {@link AbstractDAOComEvent}
	 */
	public GetNoteEvent(CouchDbClient s, DAO<Note> d, MQTT mqtt) {
		super(s,d,mqtt);
	}
	
	@Override
	protected void informCmd(JSONObject data) {
		String s = "";
		if(data.length() == 0)
			s = this.dao.getRawData();
		else // Launch correct methods from DAO
			s = this.dao.getRawData(data);
		
		JSONObject json = new JSONObject(s);
		JSONObject result = new JSONObject();
		result.put("rows",json.getJSONArray("rows"));
		
		this.publish(Controller.getInstance().getConfig().getCommandGetNote(), result);
	}


}
