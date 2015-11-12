/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import java.util.List;

import org.json.JSONObject;
import org.lightcouch.CouchDbDesign;
import org.lightcouch.DesignDocument;

import fr.utbm.to52.smarthome.model.note.Note;

/**
 * @author Alexandre Guyon
 *
 */
public class NoteDAO extends AbstractDAO<Note> {

	private static final String designDoc = "note";
	
	public void setUp(org.lightcouch.CouchDbClient s) {
		super.setUp(s);
		
		CouchDbDesign d = s.design();
		d.synchronizeAllWithDb();
		DesignDocument ds = d.getFromDesk(NoteDAO.designDoc);
		s.design().synchronizeWithDb(ds);
	}
	
	
	@Override
	public void save(Note data) {
		this.couch.save(data);
	}

	@Override
	public List<Note> getData() {
		return this.couch.view(NoteDAO.designDoc+ "/list").query(Note.class);
	}

	@Override
	public List<Note> getData(JSONObject criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
