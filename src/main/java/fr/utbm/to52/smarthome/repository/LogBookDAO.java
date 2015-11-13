/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.model.logbook.LogBook;
import fr.utbm.to52.smarthome.model.note.Note;

/**
 * @author Alexandre Guyon
 *
 */
public class LogBookDAO extends AbstractDAO<LogBook> {
	
	public void setUp(CouchDbClient s) {
		super.setUp(s);
	}
	
	@Override
	public void save(LogBook data) {
		// TODO Call notebookDAO
	}

	@SuppressWarnings("boxing")
	@Override
	public List<LogBook> getData() {
		return this.couch.view(this.designDoc+ "/list")
				.includeDocs(true)
				.query(LogBook.class);
	}

	@SuppressWarnings("boxing")
	@Override
	public String getRawData() {
		return AbstractDAO.getStringFromView(this.couch.view(this.designDoc+ "/list")
				.includeDocs(true));
	}

	@SuppressWarnings({ "unused", "boxing" })
	@Override
	public List<LogBook> getData(JSONObject criteria) {
		String day = "";

		try{
			day = criteria.getString("day");
		}catch (org.json.JSONException e) {
			System.err.println("\"day\" not found in JSON criteria");
		}
		List<Note> ln = this.couch.view(this.designDoc+ "/listByDate")
				.includeDocs(true)
				.descending(true)
				.startKey(day)
				.query(Note.class);
		
		List<LogBook> l = new LinkedList<>();
		LogBook e = new LogBook();
		e.setlNote(ln);
		l.add(e);
		
		return l;
	}

	@SuppressWarnings({ "boxing", "unused" })
	@Override
	public String getRawData(JSONObject criteria) {
		String day = "";

		try{
			day = criteria.getString("day");
		}catch (org.json.JSONException e) {
			System.err.println("\"tag\" not found in JSON criteria");
		}
		
		return AbstractDAO.getStringFromView(this.couch.view(this.designDoc+ "/listByDate")
				.includeDocs(true)
				.descending(true)
				.endKey(day));
	}	
	
}
