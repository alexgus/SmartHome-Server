/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import java.util.List;

import org.json.JSONObject;

import fr.utbm.to52.smarthome.model.logbook.LogBook;

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
		return this.couch.view(LogBookDAO.designDoc+ "/listByDay")
				.includeDocs(true)
				.descending(true)
				.endKey(day)
				.query(LogBook.class);
	}

	@Override
	public String getRawData(JSONObject criteria) {
		// TODO Auto-generated method stub
		return null;
	}	
	
}
