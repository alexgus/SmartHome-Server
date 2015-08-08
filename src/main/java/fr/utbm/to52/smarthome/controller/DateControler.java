/**
 * 
 */
package fr.utbm.to52.smarthome.controller;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import fr.utbm.to52.smarthome.calendar.ICal;
import fr.utbm.to52.smarthome.model.cron.MySchedulingPattern;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtStart;

/**
 * 
 * @author Alexandre Guyon
 */
public class DateControler implements Runnable {

	private ICal c;
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override	
	public void run() {
		try {
			this.c = new ICal(new URL("https://www.google.com/calendar/ical/alex.guyon78%40gmail.com/public/basic.ics")); // TODO conf
			this.c.load();
			
			Date now = new Date();
			Calendar tomorrowMorning = Calendar.getInstance();
			tomorrowMorning.setTime(now);
			
			tomorrowMorning.add(Calendar.DAY_OF_YEAR, 1);					
			tomorrowMorning.set(Calendar.SECOND, 0);
			tomorrowMorning.set(Calendar.MINUTE, 0);
			tomorrowMorning.set(Calendar.HOUR, 0);
			tomorrowMorning.set(Calendar.AM_PM, Calendar.AM);
			
			Calendar tomorrowNight = (Calendar) tomorrowMorning.clone();
			tomorrowNight.set(Calendar.AM_PM, Calendar.PM);
			tomorrowNight.add(Calendar.DAY_OF_YEAR, 1);
			
			ComponentList lc = this.c.get(tomorrowMorning.getTime(), tomorrowNight.getTime());
			
			if(lc.size() != 0){
				// Choose the earlier one
				VEvent search = (VEvent) lc.getComponent(Component.VEVENT);
				for (Object object : lc) {
					if(((Component)object).getName() == Component.VEVENT){
						if(search.getStartDate().getDate().after(
								((VEvent)object).getStartDate().getDate()))
							search = (VEvent) object;
					}
				}
				
				DtStart b = search.getStartDate();
				Calendar toAdd = Calendar.getInstance();
				toAdd.setTime(b.getDate());
				toAdd.add(Calendar.HOUR, -1); // TODO conf
				
				boolean sameDayLineFound = false; 
				for(int i = 0 ; i < Controller.getInstance().getCron().size() ; ++i){
					MySchedulingPattern s = new MySchedulingPattern(Controller.getInstance().getCron().getSchedulingPattern(i));
					Calendar cS = Calendar.getInstance();
					cS.setTime(s.getDate());
					int dToAdd = toAdd.get(Calendar.DAY_OF_YEAR);
					int dCs = cS.get(Calendar.DAY_OF_YEAR);
					if(dToAdd == dCs){ // If earlier in the same day or latter
						// suppress and replace
						Controller.getInstance().getCron().remove(i);
						Controller.getInstance().addRing(toAdd);
					}
				}
				if(sameDayLineFound == false)
					Controller.getInstance().addRing(toAdd);
			}else{
				for(int i = 0 ; i < Controller.getInstance().getCron().size() ; ++i){
					MySchedulingPattern s = new MySchedulingPattern(Controller.getInstance().getCron().getSchedulingPattern(i));
					Calendar cS = Calendar.getInstance();
					cS.setTime(s.getDate());
					if(tomorrowMorning.get(Calendar.DAY_OF_YEAR) == cS.get(Calendar.DAY_OF_YEAR))
						Controller.getInstance().getCron().remove(i);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
