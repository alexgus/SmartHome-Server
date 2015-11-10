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

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.controller.events.AbstractEvent#inform(java.lang.Object)
	 */
	@Override
	public void inform(Object o) {
		this.registerEvent(getClass(), o);
		
		String cmdline = (String) o;
		String json = cmdline.substring(cmdline.indexOf(" "));
		
		JSONObject j = new JSONObject(json);

		Note n1 = new Note();
		n1.setNote(j.getString("note"));
		n1.setTag(j.getString("tag"));
		
		this.dao.save(n1);
	}

}
