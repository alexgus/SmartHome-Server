/**
 * 
 */
package fr.utbm.to52.smarthome.calendar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.util.Calendars;

/**
 * Load and uses iCalendar file or URL
 * @author Alexandre Guyon
 */
public class ICal implements ICalendar {
	
	private Calendar cal;
	
	private URL url;

	/**
	 * Default constructor.
	 * @param u The URL to load the icalendar file
	 */
	public ICal(URL u){
		this.url = u;
	}
	
	/**
	 * Reception of test calendar
	 * @param arg Not used
	 */
	public static void main(String[] arg){
		ICal c;
		try {
			c = new ICal(new URL("https://www.google.com/calendar/ical/alex.guyon78%40gmail.com/public/basic.ics"));
			c.load();
			
			
			java.util.Calendar calToday = java.util.Calendar.getInstance();
			java.util.Calendar calMonthMinus1 = java.util.Calendar.getInstance();
			calMonthMinus1.set(java.util.Calendar.MONTH, 
					calToday.get(java.util.Calendar.MONTH) - 1); 
			
			ComponentList lc = c.get(calMonthMinus1.getTime(), calToday.getTime());
			
			System.out.println(lc);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}		
	}
	
	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#load()
	 */
	@Override
	public void load() {
		try {
			this.cal = Calendars.load(this.url);
		} catch (IOException | ParserException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#Add(java.util.Date, java.util.Date, java.lang.String)
	 */
	@Override
	public UUID Add(Date begin, Date end, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#Add(java.util.Date, java.util.Date, java.lang.String, java.lang.String)
	 */
	@Override
	public UUID Add(Date begin, Date end, String name, String Description) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#Add(java.util.Date, java.util.Date, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public UUID Add(Date begin, Date end, String name, String Description, String Location) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#remove(java.util.UUID)
	 */
	@Override
	public void remove(UUID uuid) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#get(net.fortuna.ical4j.filter.Filter)
	 */
	@Override
	public ComponentList get(Filter r) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#get(java.util.Date, java.util.Date)
	 */
	@Override
	public ComponentList get(Date begin, Date end) {		
		ComponentList cFiltered = new ComponentList();
		ComponentList cAll = this.cal.getComponents().getComponents(Component.VEVENT);
		
		for (Object o : cAll) {
			Component c = (Component) o;
			
			Property p1 = c.getProperty(Property.DTSTART);
			DtStart dBegin = (DtStart) p1;
			
			Property p2 = c.getProperty(Property.DTEND);
			DtEnd dEnd = (DtEnd) p2;
			
			boolean part1 = dBegin.getDate().after(begin);
			boolean part2 = dBegin.getDate().before(end);
			
			if(part1 && part2
					/*&& dEnd.getDate().before(end) && dEnd.getDate().after(begin)*/)
				cFiltered.add(c);
		}
		
		return cFiltered;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#get(java.lang.String)
	 */
	@Override
	public ComponentList get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#toICal()
	 */
	@Override
	public String toICal() {
		if(this.cal != null)
			return this.cal.toString();
		return null;
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return this.url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(URL url) {
		this.url = url;
	}
	
	/**
	 * @return the cal
	 */
	public Calendar getCal() {
		return this.cal;
	}

	/**
	 * @param cal the cal to set
	 */
	public void setCal(Calendar cal) {
		this.cal = cal;
	}

	
}