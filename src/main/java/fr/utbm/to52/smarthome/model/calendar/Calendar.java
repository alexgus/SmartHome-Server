/**
 * 
 */
package fr.utbm.to52.smarthome.model.calendar;

import java.util.Date;
import java.util.GregorianCalendar;

import net.fortuna.ical4j.model.component.VEvent;

/**
 * Class for modeling calendar.
 * @author Alexandre Guyon
 *
 */
public class Calendar extends GregorianCalendar{

	private static final long serialVersionUID = 3731532859771209791L;

	/**
	 * End for VEVENT
	 */
	public static final int START = 0;
	
	/**
	 * START for VEVENT
	 */
	public static final int END = 1;
	
	/**
	 * Create now calendar
	 */
	public Calendar(){
		super();
		this.setTime(new Date());
	}
	
	/**
	 * Get Calendar from date
	 * @param d Date to create calendar
	 */
	public Calendar(Date d){
		super();
		this.setTime(d);
	}
	
	/**
	 * Create calendar from vevent.
	 * @param ev Event to set
	 * @param endOrStart endOrStart @see {@link Calendar#START} and @see {@link Calendar#END}
	 */
	public Calendar(VEvent ev, int endOrStart){
		Date d;
		if(endOrStart == END)
			d = ev.getEndDate().getDate();
		else
			d = ev.getStartDate().getDate();
		this.setTime(d);
	}
	
}
