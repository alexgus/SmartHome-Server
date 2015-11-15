/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.model.logbook.LogBook;

/**
 * @author Alexandre Guyon
 *
 */
public class LogBookDAO extends AbstractDAO<LogBook> {
	
	public void setUp(CouchDbClient s) {
		super.setUp(s, LogBook.class);
		
		this.available_actions.add(AbstractDAO.ACTION_LISTBY);
		
		this.available_criteria.add("Date");
	}
	
	@Override
	public void save(LogBook data) {
		// TODO Call notebookDAO
	}
	
}
