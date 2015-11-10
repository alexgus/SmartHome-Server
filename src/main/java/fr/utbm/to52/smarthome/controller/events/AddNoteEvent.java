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
public class AddNoteEvent extends AbstractEvent { // FIXME Addnote specific event (uses of DAO)

	private DAO n;
	
	/**
	 * @param s {@link AbstractEvent}
	 * @param d DAO to addnote
	 */
	public AddNoteEvent(Session s, DAO<?> d) {
		super(s);
		this.n = d;
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
		
		this.n.save(n1);
	}

}
