/**
 * 
 */
package fr.utbm.to52.smarthome.controller.cronTask;

import java.net.URL;
import java.util.Calendar;

import fr.utbm.to52.smarthome.controller.Controller;
import fr.utbm.to52.smarthome.model.calendar.ICal;
import fr.utbm.to52.smarthome.model.cron.MySchedulingPattern;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * 
 * @author Alexandre Guyon
 */
public class DateController implements Runnable {

	private ICal c;
	
	private int iURL;
	
	/**
	 * Load the given URL and handle cron event for it
	 * @param url URL in the pool to load
	 */
	public DateController(int url) {
		this.iURL = url;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override	
	public void run() {
		try {
			// Load calendar
			this.c = new ICal(new URL(Controller.getInstance().getConfig().getAlarmURL().get(this.iURL)));
			this.c.load();
			
			// Create instance of calendar
			fr.utbm.to52.smarthome.model.calendar.Calendar now = new fr.utbm.to52.smarthome.model.calendar.Calendar();
			fr.utbm.to52.smarthome.model.calendar.Calendar rangeHour0 = (fr.utbm.to52.smarthome.model.calendar.Calendar) now.clone();
			fr.utbm.to52.smarthome.model.calendar.Calendar rangeHour1 = (fr.utbm.to52.smarthome.model.calendar.Calendar) now.clone();
			
			// Create range of date for the research
			if(now.get(Calendar.AM_PM) == Calendar.AM && now.get(Calendar.HOUR) < 4) // if soon in the morning
				rangeHour0 = now;
			else{
				rangeHour0.set(Calendar.SECOND, 0);
				rangeHour0.set(Calendar.MINUTE, 0);
				rangeHour0.set(Calendar.HOUR, 0);
				rangeHour0.set(Calendar.AM_PM, Calendar.AM);
				rangeHour0.add(Calendar.DAY_OF_YEAR, 1);
			}
			
			rangeHour1.set(Calendar.SECOND, 0);
			rangeHour1.set(Calendar.MINUTE, 0);
			rangeHour1.set(Calendar.HOUR, 12);
			rangeHour1.set(Calendar.AM_PM, Calendar.PM);
			rangeHour1.add(Calendar.DAY_OF_YEAR, 1);
			
			// Get events from this range of time
			ComponentList lc = this.c.get(rangeHour0.getTime(), rangeHour1.getTime());
			
			if(lc.size() != 0){ // If there's event
				
				// Choose the earlier one
				fr.utbm.to52.smarthome.model.calendar.Calendar toAdd = new fr.utbm.to52.smarthome.model.calendar.Calendar(getEarlier(lc), 
						fr.utbm.to52.smarthome.model.calendar.Calendar.START);
				toAdd = setWakeUpTime(toAdd);
				
				if(Controller.getInstance().getCron().size() == 0) // If no ring is scheduled
					Controller.getInstance().getCron().addRing(toAdd,Controller.SOURCE_ICAL);
				else{ 
					// If there is others rings
					// For each ring
					for(int i = 0 ; i < Controller.getInstance().getCron().size() ; ++i){
						// Cast scheduling in Calendar object
						fr.utbm.to52.smarthome.model.calendar.Calendar sched = 
								new fr.utbm.to52.smarthome.model.calendar.Calendar(
								new MySchedulingPattern(
										Controller.getInstance().getCron().getSchedulingPattern(i))
								.getDate());
						
						// tweak it
						sched.set(Calendar.MILLISECOND, 0);
						sched.set(Calendar.SECOND, 0);
						sched.set(Calendar.AM_PM, Calendar.AM);
						toAdd.set(Calendar.MILLISECOND, 0);
						toAdd.set(Calendar.SECOND, 0);
						
						// suppress and replace
						if(!sched.getTime().toString().equals(toAdd.getTime().toString())
								&& sched.get(Calendar.DAY_OF_YEAR) == toAdd.get(Calendar.DAY_OF_YEAR)){ 
							// If not the same schedule and the same day
							Controller.getInstance().getCron().remove(i); // remove the old schedule
							Controller.getInstance().getCron().addRing(toAdd,Controller.SOURCE_ICAL);
						}
					}
				}	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get earlier event
	 * @param lc The list to search
	 * @return The earlier VEvent
	 */
	public static VEvent getEarlier(ComponentList lc){
		VEvent search = (VEvent) lc.getComponent(Component.VEVENT);
		for (Object object : lc) {
			if(((Component)object).getName() == Component.VEVENT){
				if(search.getStartDate().getDate().after(
						((VEvent)object).getStartDate().getDate()))
					search = (VEvent) object;
			}
		}
		return search;
	}
	
	private static fr.utbm.to52.smarthome.model.calendar.Calendar setWakeUpTime(fr.utbm.to52.smarthome.model.calendar.Calendar c){
		int wakeUpBeforeH = Integer.parseInt(Controller.getInstance().getConfig().getAlarmWakeUpTime().split("h")[0]);
		int wakeUpBeforeM = Integer.parseInt(Controller.getInstance().getConfig().getAlarmWakeUpTime().split("h")[1]);
	
		c.add(Calendar.HOUR, -wakeUpBeforeH);
		c.add(Calendar.MINUTE, -wakeUpBeforeM);
		
		return c;
	}
	
}
