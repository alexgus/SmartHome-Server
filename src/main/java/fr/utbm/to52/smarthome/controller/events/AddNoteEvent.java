/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import org.hibernate.Session;
import org.json.JSONObject;

import fr.utbm.to52.smarthome.model.note.Note;
import fr.utbm.to52.smarthome.repository.DAO;

/**
 * @author Alexandre Guyon
 *
 */
public class AddNoteEvent extends AbstractDAOEvent<Note> {
	
	/**
	 * @param s {@link AbstractEvent}
	 * @param d {@link AbstractDAOEvent}
	 */
	public AddNoteEvent(Session s, DAO<Note> d) {
		super(s,d);
	}

	@Override
	public void informCmd(JSONObject data) {
		Note n1 = new Note();
		n1.setNote(data.getString("note"));
		n1.setTag(data.getString("tag"));
		
		this.dao.save(n1);
	}
}
