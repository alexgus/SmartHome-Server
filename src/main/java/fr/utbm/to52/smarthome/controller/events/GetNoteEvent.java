/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import java.util.List;

import org.json.JSONObject;
import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.model.note.Note;
import fr.utbm.to52.smarthome.repository.DAO;

/**
 * @author Alexandre Guyon
 *
 */
public class GetNoteEvent extends AbstractDAOEvent<Note> {

	/**
	 * @param s {@link AbstractEvent}
	 * @param d {@link AbstractDAOEvent}
	 */
	public GetNoteEvent(CouchDbClient s, DAO<Note> d) {
		super(s,d);
	}
	
	@Override
	protected void informCmd(JSONObject data) {
		List<Note> ln = this.dao.getData();
		
		System.out.println(data);
		System.out.println(ln);
		// TODO Get note
		// if args
			// Launch correct methods from DAO
		
	}


}
