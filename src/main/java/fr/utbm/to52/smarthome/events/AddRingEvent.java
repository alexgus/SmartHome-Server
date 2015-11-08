/**
 * 
 */
package fr.utbm.to52.smarthome.events;

import java.util.Calendar;

import org.json.JSONObject;

import fr.utbm.to52.smarthome.controller.Conf;
import fr.utbm.to52.smarthome.model.cron.RingCron;

// FIXME ADD code for adding ring (what does the parser do ?)
/**
 * This event add ring to cron from user input in JSON
 * <code></code>
 * @author Alexandre Guyon
 *
 */
public class AddRingEvent implements Event {

	private RingCron rcron;
	
	/**
	 * Constructor taking RingCron object for adding ring to it.
	 * @param r The ring cron to object to add a date 
	 */
	public AddRingEvent(RingCron r) {
		this.rcron = r;
	}
	
	@Override
	public void inform(Object o) {
		String s = (String) o;
	
		String s0 = s.split(" ")[1];
		JSONObject json = new JSONObject(s0);
		String data = json.getString("date");
		String[] data2 = data.split("H");
		String[] date = data2[0].split("-");
		String[] clock = data2[1].split("M");
		
		Calendar c = Calendar.getInstance();
		
		c.set(Integer.parseInt(date[0]), 
				Integer.parseInt(date[1]) - 1, // 0 => January
				Integer.parseInt(date[2]), 
				Integer.parseInt(clock[0]), 
				Integer.parseInt(clock[1]), 0); // Not enough precise for seconds due to cron
		
		this.rcron.addRing(c,Conf.SOURCE_NET);
	}

}
