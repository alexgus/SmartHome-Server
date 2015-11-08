package fr.utbm.to52.smarthome.events;

// TODO add system event (ex light) and user event (user can launch)

/**
 * This used to send a basic event. 
 * @author Alexandre Guyon
 *
 */
public interface Event {

	/**
	 * This send a basic event with an Object as argument.
	 * @param o (optional) Send data with the event. 
	 */
	public void inform(Object o);
	
}
