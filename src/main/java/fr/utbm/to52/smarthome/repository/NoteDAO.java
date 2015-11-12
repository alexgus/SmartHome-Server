/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONObject;

import fr.utbm.to52.smarthome.model.note.Note;
import fr.utbm.to52.smarthome.util.BasicIO;

/**
 * @author Alexandre Guyon
 *
 */
@SuppressWarnings("boxing")
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

	@Override
	public List<Note> getData() {
		return this.couch.view(NoteDAO.designDoc+ "/list")
				.includeDocs(true)
				.query(Note.class);
	}
	
	@Override
	public String getRawData() {
		return this.couch.view(NoteDAO.designDoc+ "/list")
				.includeDocs(true)
				.queryForString();
	}

	@SuppressWarnings("unused")
	@Override
	public List<Note> getData(JSONObject criteria) {
		String tag = "";

		try{
			tag = criteria.getString("tag");
		}catch (org.json.JSONException e) {
			System.err.println("\"tag\" not found in JSON criteria");
		}
		return this.couch.view(NoteDAO.designDoc+ "/listByTag")
				.includeDocs(true)
				.descending(true)
				.endKey(tag)
				.query(Note.class);
	}
	
	@SuppressWarnings({ "unused", "resource" })
	@Override
	public String getRawData(JSONObject criteria) {
		String tag = "";
		String content = "";

		try{
			tag = criteria.getString("tag"); // FIXME UGLY
		}catch (org.json.JSONException e) {
			System.err.println("\"tag\" not found in JSON criteria");
		}
		
		InputStream is = this.couch.view(NoteDAO.designDoc+ "/listByTag")
				.includeDocs(true)
				.descending(true)
				.endKey(tag)
				.queryForStream();
		
		try {
			content = BasicIO.readInputStream(is);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return content;
	}

}
