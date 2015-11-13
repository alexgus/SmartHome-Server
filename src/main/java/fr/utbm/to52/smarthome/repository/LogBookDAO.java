/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import fr.utbm.to52.smarthome.model.logbook.LogBook;
import fr.utbm.to52.smarthome.model.note.Note;
import fr.utbm.to52.smarthome.util.BasicIO;

/**
 * @author Alexandre Guyon
 *
 */
public class LogBookDAO extends AbstractDAO<LogBook> {

	private static final String designDoc = "logbook";
	
	public void setUp(org.lightcouch.CouchDbClient s) {
		super.setUp(s);
		s.design().synchronizeWithDb(s.design().getFromDesk(LogBookDAO.designDoc));
	}
	
	@Override
	public void save(LogBook data) {
		// Nothing to save
	}

	@SuppressWarnings("boxing")
	@Override
	public List<LogBook> getData() {
		return this.couch.view(LogBookDAO.designDoc+ "/list")
				.includeDocs(true)
				.query(LogBook.class);
	}

	@SuppressWarnings("boxing")
	@Override
	public String getRawData() {
		return this.couch.view(LogBookDAO.designDoc+ "/list")
				.includeDocs(true)
				.queryForString();
	}

	@SuppressWarnings({ "unused", "boxing" })
	@Override
	public List<LogBook> getData(JSONObject criteria) {
		String day = "";

		try{
			day = criteria.getString("day");
		}catch (org.json.JSONException e) {
			System.err.println("\"tag\" not found in JSON criteria");
		}
		List<Note> ln = this.couch.view(LogBookDAO.designDoc+ "/listByDate")
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

	@SuppressWarnings({ "boxing", "resource", "unused" })
	@Override
	public String getRawData(JSONObject criteria) {
		String day = "";

		try{
			day = criteria.getString("day");
		}catch (org.json.JSONException e) {
			System.err.println("\"tag\" not found in JSON criteria");
		}
		InputStream is = this.couch.view(LogBookDAO.designDoc+ "/listByDate")
				.includeDocs(true)
				.descending(true)
				.endKey(day)
				.queryForStream();
		
		String ln = BasicIO.readInputStream(is);
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ln.toString();
	}	
	
}
