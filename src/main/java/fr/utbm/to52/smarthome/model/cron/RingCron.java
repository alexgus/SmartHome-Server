/**
 * 
 */
package fr.utbm.to52.smarthome.model.cron;

import java.util.Calendar;

import fr.utbm.to52.smarthome.controller.Conf;
import fr.utbm.to52.smarthome.controller.Controller;
import it.sauronsoftware.cron4j.ProcessTask;
import it.sauronsoftware.cron4j.SchedulingPattern;

/**
 * @author Alexandre Guyon
 *
 */
public abstract class RingCron extends Cron {

	/**
	 * @see fr.utbm.to52.smarthome.model.cron.Cron#Cron()
	 */
	public RingCron() {
		super();
	}

	/**
	 * @see fr.utbm.to52.smarthome.model.cron.Cron#Cron(String)
	 * @param user @see fr.utbm.to52.smarthome.model.cron.Cron#Cron(String)
	 */
	public RingCron(String user) {
		super(user);
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.model.cron.Cron#apply()
	 */
	@Override
	protected abstract void apply();

	/**
	 * Add ring in the crontab with the corresponding date
	 * @param c The date to ring
	 * @param INPUT Controller source
	 */
	public void addRing(Calendar c, int INPUT){
		ProcessTask p;
		
		String host = "localhost";
		String port = Controller.getInstance().getConfig().getServerPort() + "";
		String CMD = Controller.getInstance().getConfig().getCommandRing();
		
		if(INPUT == Conf.SOURCE_ICAL)
			p = new ProcessTask("echo " + CMD + " | nc " + host + " " + port
					+ " " + Controller.getInstance().getConfig().getCronICalTag());
		else
			p = new ProcessTask("echo " + CMD + " | nc " + host + " " + port
					+ " " + Controller.getInstance().getConfig().getCronTag());

		int min = c.get(Calendar.MINUTE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int d = c.get(Calendar.DAY_OF_MONTH);
		int m = c.get(Calendar.MONTH) + 1;

		this.add(new SchedulingPattern(min +" " + hour + " " + d + " " + m + " *" ), p);
	}
}
