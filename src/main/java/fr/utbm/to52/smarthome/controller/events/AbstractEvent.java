/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import java.util.Date;

import org.lightcouch.CouchDbClient;

/**
 * @author Alexandre Guyon
 *
 */
public abstract class AbstractEvent implements Event {
	
	private CouchDbClient couch;
	
	/**
	 * Set the hibernate session to the event
	 * @param s Event to session
	 */
	public AbstractEvent(CouchDbClient s) {
		this.couch = s;
	}
    
	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.controller.events.Event#inform(java.lang.Object)
	 */
	@Override
	public abstract void inform(Object o);
	
	/**
	 * Register event
	 * @param c Class event
	 * @param payload Payload to store
	 */
	protected void registerEvent(Class<?> c, Object payload){
		StorableEvent s = new StorableEvent();
		s.setDate(new Date());
		s.setEventName(c.getName());
		if(payload != null)
			s.setPayload(payload.toString());
		
		this.couch.save(s);		
	}
	
	/**
	 * @return Return couchdb client
	 */
	protected CouchDbClient getCouchDb() {
		return this.couch;
	}

}
