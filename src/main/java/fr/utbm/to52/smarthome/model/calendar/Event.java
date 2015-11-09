/**
 * 
 */
package fr.utbm.to52.smarthome.model.calendar;

import java.util.Date;

import fr.utbm.to52.smarthome.model.location.Location;

/**
 * @author Alexandre Guyon
 *
 */
public class Event {

	private Date begin;
	
	private Date end;
	
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
	public Date getBegin() {
		return this.begin;
	}

	/**
	 * @param begin the begin to set
	 */
	public void setBegin(Date begin) {
		this.begin = begin;
	}

	/**
	 * @return the end
	 */
	public Date getEnd() {
		return this.end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
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
