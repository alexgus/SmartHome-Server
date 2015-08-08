/**
 * 
 */
package fr.utbm.to52.smarthome.model.calendar;

import java.util.Date;
import java.util.UUID;

import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.model.ComponentList;

/**
 * @author Alexandre Guyon
 *
 */
public class CalDav implements ICalendar {

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#load()
	 */
	@Override
	public void load() {
		// 
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#Add(java.util.Date, java.util.Date, java.lang.String)
	 */
	@Override
	public UUID Add(Date begin, Date end, String name) {
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#Add(java.util.Date, java.util.Date, java.lang.String, java.lang.String)
	 */
	@Override
	public UUID Add(Date begin, Date end, String name, String Description) {
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#Add(java.util.Date, java.util.Date, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public UUID Add(Date begin, Date end, String name, String Description, String Location) {
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#remove(java.util.UUID)
	 */
	@Override
	public void remove(UUID uuid) {
		//
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#get(net.fortuna.ical4j.filter.Filter)
	 */
	@Override
	public ComponentList get(Filter r) {
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#get(java.util.Date, java.util.Date)
	 */
	@Override
	public ComponentList get(Date begin, Date end) {
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#get(java.lang.String)
	 */
	@Override
	public ComponentList get(String name) {
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#toICal()
	 */
	@Override
	public String toICal() {
		return null;
	}

}
