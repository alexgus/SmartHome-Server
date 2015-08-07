package fr.utbm.to52.smarthome.calendar;

import java.util.Date;
import java.util.UUID;

import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.model.ComponentList;

/**
 * Define all basic method for manipulating a calendar
 * @author Alexandre Guyon
 *
 */
public interface ICalendar {
	
	/**
	 * Get the calendar from the web
	 */
	public void load();
	
	/**
	 * Add an event to the calendar
	 * @param begin Begin date
	 * @param end End date
	 * @param name Name of the event
	 * @return Thu UUID of this event
	 */
	public UUID Add(Date begin, Date end, String name);
	
	/**
	 * Add an event to the calendar
	 * @param begin Begin date
	 * @param end End date
	 * @param name Name of the event
	 * @param Description Description of this event
	 * @return Thu UUID of this event
	 */
	public UUID Add(Date begin, Date end, String name, String Description);
	
	/**
	 * Add an event to the calendar
	 * @param begin Begin date
	 * @param end End date
	 * @param name Name of the event
	 * @param Description Description of this event
	 * @param Location Location of this event
	 * @return Thu UUID of this event
	 */
	public UUID Add(Date begin, Date end, String name, String Description, String Location);
	
	/**
	 * Remove this event
	 * @param uuid Event to remove
	 */
	public void remove(UUID uuid);
	
	/**
	 * Get Events corresponding to the filter given
	 * @param r The filter to match
	 * @return Events corresponding to the filter given
	 */
	public ComponentList get(Filter r);
	
	/**
	 * Get Events corresponding to the filter given
	 * @param begin Begin date
	 * @param end End date
	 * @return Events corresponding to the filter given
	 */
	public ComponentList get(Date begin, Date end);
	
	/**
	 * Get Events corresponding to the filter given
	 * @param name Name of the event
	 * @return Events corresponding to the filter given
	 */
	public ComponentList get(String name);
	
	/**
	 * Print iCal
	 * @return String corresponding to iCal
	 */
	public String toICal(); 
	
}
