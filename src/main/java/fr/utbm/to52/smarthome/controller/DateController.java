/**
 * 
 */
package fr.utbm.to52.smarthome.controller;

import java.net.URL;
import java.util.Calendar;

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
			this.c = new ICal(new URL(Controller.getInstance().getConfig().getAlarmURL().get(this.iURL)));
			this.c.load();
			
			int wakeUpBeforeH = Integer.parseInt(Controller.getInstance().getConfig().getAlarmWakeUpTime().split("h")[0]);
			int wakeUpBeforeM = Integer.parseInt(Controller.getInstance().getConfig().getAlarmWakeUpTime().split("h")[1]);
			
			fr.utbm.to52.smarthome.model.calendar.Calendar now = new fr.utbm.to52.smarthome.model.calendar.Calendar();
			fr.utbm.to52.smarthome.model.calendar.Calendar rangeHour0 = (fr.utbm.to52.smarthome.model.calendar.Calendar) now.clone();
			fr.utbm.to52.smarthome.model.calendar.Calendar rangeHour1 = (fr.utbm.to52.smarthome.model.calendar.Calendar) now.clone();
			
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
			
			// All events
			/*rangeHour0.add(Calendar.HOUR, +wakeUpBeforeH);
			rangeHour0.add(Calendar.MINUTE, +wakeUpBeforeM + 1); // +1 for being sure 
			
			rangeHour1.add(Calendar.HOUR, +wakeUpBeforeH +20); // +20 : day of 20 hours for being sure to trigger // TODO Conf
			rangeHour1.add(Calendar.MINUTE, +wakeUpBeforeM);*/
			
			// Get events from this range of time
			ComponentList lc = this.c.get(rangeHour0.getTime(), rangeHour1.getTime());
			
			if(lc.size() != 0){ // If there's event
				// Choose the earlier one
				fr.utbm.to52.smarthome.model.calendar.Calendar toAdd = new fr.utbm.to52.smarthome.model.calendar.Calendar(getEarlier(lc), 
						fr.utbm.to52.smarthome.model.calendar.Calendar.START);
				toAdd.add(Calendar.HOUR, -wakeUpBeforeH);
				toAdd.add(Calendar.MINUTE, -wakeUpBeforeM);
				
				boolean sameDayLineFound = false;
				for(int i = 0 ; i < Controller.getInstance().getCron().size() ; ++i){
					MySchedulingPattern s = new MySchedulingPattern(Controller.getInstance().getCron().getSchedulingPattern(i));
					fr.utbm.to52.smarthome.model.calendar.Calendar cS = new fr.utbm.to52.smarthome.model.calendar.Calendar(s.getDate());
					if(cS.getTime().after(toAdd.getTime())){ // If earlier in the same day or latter
						// suppress and replace
						Controller.getInstance().getCron().remove(i);
						Controller.getInstance().addRing(toAdd,Controller.SOURCE_ICAL);
						sameDayLineFound = true;
					}
				}
				if(sameDayLineFound == false)
					Controller.getInstance().addRing(toAdd,Controller.SOURCE_ICAL);
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
	
}
