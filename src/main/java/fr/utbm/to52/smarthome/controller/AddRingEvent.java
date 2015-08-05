/**
 * 
 */
package fr.utbm.to52.smarthome.controller;

import java.util.Calendar;

import org.json.JSONObject;

/**
 * This event add ring to cron
 * @author Alexandre Guyon
 *
 */
public class AddRingEvent implements Event {

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
		
		Controller.getInstance().addRing(c);
	}

}
