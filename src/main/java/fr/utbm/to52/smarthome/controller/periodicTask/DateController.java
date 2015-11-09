/**
 * 
 */
package fr.utbm.to52.smarthome.controller.periodicTask;

import java.net.URL;
import java.util.Calendar;
import java.util.TimerTask;

import fr.utbm.to52.smarthome.controller.Conf;
import fr.utbm.to52.smarthome.controller.Controller;
import fr.utbm.to52.smarthome.model.calendar.ICal;
import fr.utbm.to52.smarthome.model.cron.MySchedulingPattern;
import fr.utbm.to52.smarthome.model.cron.RingCron;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.VEvent;

// TODO Delete cron. Re-write cron to encapsulate Timer instead of cron 

/**
 * 
 * @author Alexandre Guyon
 */
public class DateController extends TimerTask {

	private ICal c;
	
	private int iURL;
	
	private RingCron rcron;
	
	/**
	 * Load the given URL and handle cron event for it
	 * @param r The ring cron object for adding ring to it
	 * @param url URL in the pool to load
	 */
	public DateController(RingCron r, int url) {
		this.iURL = url;
		this.rcron = r;
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
			fr.utbm.to52.smarthome.model.calendar.DateCalendar now = new fr.utbm.to52.smarthome.model.calendar.DateCalendar();
			fr.utbm.to52.smarthome.model.calendar.DateCalendar rangeHour0 = (fr.utbm.to52.smarthome.model.calendar.DateCalendar) now.clone();
			fr.utbm.to52.smarthome.model.calendar.DateCalendar rangeHour1 = (fr.utbm.to52.smarthome.model.calendar.DateCalendar) now.clone();
			
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
				fr.utbm.to52.smarthome.model.calendar.DateCalendar toAdd = new fr.utbm.to52.smarthome.model.calendar.DateCalendar(getEarlier(lc), 
						fr.utbm.to52.smarthome.model.calendar.DateCalendar.START);
				toAdd = setWakeUpTime(toAdd);
				
				if(this.rcron.size() == 0) // If no ring is scheduled
					this.rcron.addRing(toAdd,Conf.SOURCE_ICAL);
				else{ 
					// If there is others rings
					// For each ring
					for(int i = 0 ; i < this.rcron.size() ; ++i){
						// Cast scheduling in Calendar object
						fr.utbm.to52.smarthome.model.calendar.DateCalendar sched = 
								new fr.utbm.to52.smarthome.model.calendar.DateCalendar(
								new MySchedulingPattern(this.rcron.getSchedulingPattern(i)).getDate());
						
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
							this.rcron.remove(i); // remove the old schedule
							this.rcron.addRing(toAdd,Conf.SOURCE_ICAL);
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
	
	private static fr.utbm.to52.smarthome.model.calendar.DateCalendar setWakeUpTime(fr.utbm.to52.smarthome.model.calendar.DateCalendar c){
		int wakeUpBeforeH = Integer.parseInt(Controller.getInstance().getConfig().getAlarmWakeUpTime().split("h")[0]);
		int wakeUpBeforeM = Integer.parseInt(Controller.getInstance().getConfig().getAlarmWakeUpTime().split("h")[1]);
	
		c.add(Calendar.HOUR, -wakeUpBeforeH);
		c.add(Calendar.MINUTE, -wakeUpBeforeM);
		
		return c;
	}
	
}
