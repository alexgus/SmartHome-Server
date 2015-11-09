/**
 * 
 */
package fr.utbm.to52.smarthome.model.calendar;

import fr.utbm.to52.smarthome.model.location.Location;

/**
 * @author Alexandre Guyon
 *
 */
public class Event {

	private DateCalendar begin;
	
	private DateCalendar end;
	
	private String title;
	
	private Location loc;
	
	/**
	 * 
	 */
	public Event() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the begin
	 */
	public DateCalendar getBegin() {
		return this.begin;
	}

	/**
	 * @param begin the begin to set
	 */
	public void setBegin(DateCalendar begin) {
		this.begin = begin;
	}

	/**
	 * @return the end
	 */
	public DateCalendar getEnd() {
		return this.end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(DateCalendar end) {
		this.end = end;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the loc
	 */
	public Location getLoc() {
		return this.loc;
	}

	/**
	 * @param loc the loc to set
	 */
	public void setLoc(Location loc) {
		this.loc = loc;
	}

}
