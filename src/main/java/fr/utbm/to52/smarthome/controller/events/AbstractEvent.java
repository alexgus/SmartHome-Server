/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import java.util.Date;

import org.hibernate.ogm.OgmSession;

/**
 * @author Alexandre Guyon
 *
 */
public abstract class AbstractEvent implements Event {
	
	private OgmSession hbm;
	
	/**
	 * Set the hibernate session to the event
	 * @param s Event to session
	 */
	public AbstractEvent(OgmSession s) {
		this.hbm = s;
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
		
		this.hbm.beginTransaction();
		
		this.hbm.save(s);
		this.hbm.flush();
		
		this.hbm.getTransaction().commit();
		
	}

}
