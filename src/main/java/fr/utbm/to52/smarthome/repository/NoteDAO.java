/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import fr.utbm.to52.smarthome.model.note.Note;

/**
 * @author Alexandre Guyon
 *
 */
public class NoteDAO extends AbstractDAO<Note> {
	
	public void setUp(org.lightcouch.CouchDbClient s) {
		super.setUp(s, Note.class);
		this.available_actions.add(AbstractDAO.ACTION_SAVE);
		this.available_actions.add(AbstractDAO.ACTION_LIST);
		this.available_actions.add(AbstractDAO.ACTION_LISTBY);
		
		this.available_criteria.add("Tag");
		this.available_criteria.add("Date");
	}

}
