/**
 * 
 */
package fr.utbm.to52.smarthome.controller.cronTask;

import java.util.Date;

import fr.utbm.to52.smarthome.model.cron.Cron;
import fr.utbm.to52.smarthome.model.cron.MySchedulingPattern;

/**
 * Remove old cron line
 * @author Alexandre Guyon
 *
 */
public class CronCleaner implements Runnable {

	private Cron cron;
	
	/**
	 * Default Constructor
	 * @param c Cron to clean
	 */
	public CronCleaner(Cron c) {
		this.cron = c;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		for(int i = 0 ; i < this.cron.size() ; ++i){
			MySchedulingPattern s = new MySchedulingPattern(this.cron.getSchedulingPattern(i));
			Date now = new Date();
			if(s.getDate().before(now))
				this.cron.remove(i);
		}
	}

}
