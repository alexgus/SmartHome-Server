/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.controller.events.StorableEvent;

/**
 * @author Alexandre Guyon
 *
 */
public class EventDAO extends AbstractDAO<StorableEvent>{

	@Override
	public void setUp(CouchDbClient s) {
		super.setUp(s, StorableEvent.class);
		
		this.available_actions.add(AbstractDAO.ACTION_SAVE);
		this.available_actions.add(AbstractDAO.ACTION_LIST);
		this.available_actions.add(AbstractDAO.ACTION_LISTBY);
		
		this.available_criteria.add("Date");
		this.available_criteria.add("EventName");
		this.available_criteria.add("Count");
	}


}
