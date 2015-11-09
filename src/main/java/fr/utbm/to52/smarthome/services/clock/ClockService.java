/**
 * 
 */
package fr.utbm.to52.smarthome.services.clock;

import java.util.Timer;
import java.util.TimerTask;

import fr.utbm.to52.smarthome.controller.Conf;
import fr.utbm.to52.smarthome.controller.cronTask.CronCleaner;
import fr.utbm.to52.smarthome.controller.cronTask.DateController;
import fr.utbm.to52.smarthome.model.cron.JavaCron;
import fr.utbm.to52.smarthome.model.cron.RingCron;
import fr.utbm.to52.smarthome.model.cron.SystemCron;
import fr.utbm.to52.smarthome.services.AbstractService;
import it.sauronsoftware.cron4j.Scheduler;

/**
 * @author Alexandre Guyon
 *
 */
public class ClockService extends AbstractService {
	
	private Timer sched;
	
	private TimerTask cleaner;
	
	private RingCron cron;
	
	private Scheduler mainCron;
	
	/**
	 * 
	 */
	public ClockService() {
		this.mainCron = new Scheduler();
		// TODO see sched deamon
		this.sched = new Timer();
	}
	
	public void setUp(Conf c) {
		super.setUp(c);
		
		if(this.config.getCronSource().equals(Conf.CRON_SYSTEM))
			this.cron = new SystemCron();
		else
			this.cron = new JavaCron();
		
		for(int i = 0 ; i < this.config.getAlarmURL().size() ; ++i)
			this.mainCron.schedule("* * * * *", new DateController(this.cron, i));
		
		this.cleaner = new CronCleaner(this.cron);
		// TODO add Constant/config
		this.sched.scheduleAtFixedRate(this.cleaner, 0, 60 * 1000 * 60);
	}
	

	@Override
	public void start() {
		this.mainCron.start();
	}

	@Override
	public void stop() {
		this.mainCron.stop();
	}
	
	/**
	 * @return the cron
	 */
	public RingCron getCron() {
		return this.cron;
	}

	/**
	 * @param cron the cron to set
	 */
	public void setCron(RingCron cron) {
		this.cron = cron;
	}

}
