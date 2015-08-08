/**
 * 
 */
package fr.utbm.to52.smarthome.model;

import java.util.Calendar;
import java.util.Date;

import it.sauronsoftware.cron4j.InvalidPatternException;
import it.sauronsoftware.cron4j.SchedulingPattern;

/**
 * Add some useful methods to SchedulingPattern
 * 
 * @author Alexandre Guyon
 *
 */
public class MySchedulingPattern extends SchedulingPattern {

	/**
	 * Default constructor
	 * @param arg0 @see SchedulingPattern#SchedulingPattern(String)
	 * @throws InvalidPatternException No god patter in given string
	 */
	public MySchedulingPattern(String arg0) throws InvalidPatternException {
		super(arg0);
	}
	
	/**
	 * Construct scheduling pattern thanks to superclass
	 * @param s Scheduling pattern to use
	 */
	public MySchedulingPattern(SchedulingPattern s){
		this(s.toString());
	}
	
	/**
	 * Get the date of the Scheduling pattern
	 * @return The Date of the trigger of the scheduling pattern
	 */
	public Date getDate(){
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		
		String[] pattern = this.toString().split(" ");
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, Integer.parseInt(pattern[0]));
		c.set(Calendar.HOUR, Integer.parseInt(pattern[1]));
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(pattern[2]));
		c.set(Calendar.MONTH, Integer.parseInt(pattern[3]) - 1);
		
		return c.getTime();
	}
	
}
