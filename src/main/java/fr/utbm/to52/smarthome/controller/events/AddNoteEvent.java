/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.json.JSONObject;
import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.model.note.Note;
import fr.utbm.to52.smarthome.repository.DAO;
import fr.utbm.to52.smarthome.repository.UnimplementedOperationException;

/**
 * @author Alexandre Guyon
 *
 */
public class AddNoteEvent extends AbstractDAOEvent<Note> {
	
	/**
	 * @param s {@link AbstractEvent}
	 * @param d {@link AbstractDAOEvent}
	 */
	public AddNoteEvent(CouchDbClient s, DAO<Note> d) {
		super(s,d);
	}

	@Override
	public void informCmd(JSONObject data) {
		Note n1 = new Note();
		n1.setNote(data.getString("note"));
		n1.setTag(data.getString("tag"));
		
		try{
			this.dao.save(n1);
		}catch(UnimplementedOperationException e){
			e.printStackTrace();
		}
	}
}
