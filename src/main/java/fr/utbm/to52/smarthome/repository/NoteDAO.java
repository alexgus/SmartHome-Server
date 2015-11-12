/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import java.util.List;

import org.json.JSONObject;

import fr.utbm.to52.smarthome.model.note.Note;

/**
 * @author Alexandre Guyon
 *
 */
public class NoteDAO extends AbstractDAO<Note> {

	private static final String designDoc = "note";
	
	public void setUp(org.lightcouch.CouchDbClient s) {
		super.setUp(s);
		s.design().synchronizeWithDb(s.design().getFromDesk(NoteDAO.designDoc));
	}
	
	
	@Override
	public void save(Note data) {
		this.couch.save(data);
	}

	@SuppressWarnings("boxing")
	@Override
	public List<Note> getData() {
		return this.couch.view(NoteDAO.designDoc+ "/list").includeDocs(true).query(Note.class);
	}

	@Override
	public List<Note> getData(JSONObject criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
