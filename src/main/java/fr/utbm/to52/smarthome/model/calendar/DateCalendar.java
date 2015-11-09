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
public class DateCalendar extends GregorianCalendar{

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
	public DateCalendar(){
		super();
		this.setTime(new Date());
	}
	
	/**
	 * Get Calendar from date
	 * @param d Date to create calendar
	 */
	public DateCalendar(Date d){
		super();
		this.setTime(d);
	}
	
	/**
	 * Create calendar from vevent.
	 * @param ev Event to set
	 * @param endOrStart endOrStart @see {@link DateCalendar#START} and @see {@link DateCalendar#END}
	 */
	public DateCalendar(VEvent ev, int endOrStart){
		Date d;
		if(endOrStart == END)
			d = ev.getEndDate().getDate();
		else
			d = ev.getStartDate().getDate();
		this.setTime(d);
	}
	
}
