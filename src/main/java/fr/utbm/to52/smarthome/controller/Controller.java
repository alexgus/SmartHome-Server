package fr.utbm.to52.smarthome.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import fr.utbm.to52.smarthome.calendar.ICal;
import fr.utbm.to52.smarthome.events.AddRingEvent;
import fr.utbm.to52.smarthome.events.RingEvent;
import fr.utbm.to52.smarthome.model.Cron;
import fr.utbm.to52.smarthome.network.MQTT;
import fr.utbm.to52.smarthome.network.SocketInput;
import it.sauronsoftware.cron4j.ProcessTask;
import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
import net.fortuna.ical4j.model.ComponentList;

/**
 * This controller is a singleton. For getting an instance from it 
 * <code>Controller.getInstance()</code> This initialize it with 
 * the default configuration file if it was not created before.
 * 
 * @author Alexandre Guyon
 *
 */
public class Controller {

	/**
	 * Singleton instance for this controller 
	 */
	private static Controller singleton = null;

	/**
	 * Get an instance from the controller. The first call initialize it with the default configuration file.
	 * @return Singleton instance of this controller
	 */
	public static Controller getInstance(){
		if(singleton == null)
			singleton = new Controller();
		return singleton;
	}

	private SocketInput server;
	private Thread mainThread;

	private MQTT mqtt;

	private Conf config;

	private Cron cron;
	
	private Scheduler jcron;

	private CommandHandlerImpl cmdHandler;

	private Controller(){
		this.config = new Conf();
		this.config.importConf();
		this.cmdHandler = new CommandHandlerImpl();
		this.jcron = new Scheduler();
		this.mqtt = new MQTT(this.config.getMQTTID(), this.config.getMQTTServer(), this.config.getMQTTQOS(), this.cmdHandler);
	}

	/**
	 * Start the server
	 */
	public void start(){
		this.cron = new Cron();

		this.mqtt.connect();

		this.cmdHandler.setRingEventController(new RingEvent(this.mqtt));
		this.cmdHandler.setAddRingEventController(new AddRingEvent());

		try {
			this.server = new SocketInput(this.getConfig().getServerPort());
			this.server.setCmdHandler(this.cmdHandler);

			this.mainThread = new Thread(this.server);
			this.mainThread.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.jcron.schedule("* * * * *", new Runnable() {
			
			private ICal c;
			
			public void run() {
				try {
					this.c = new ICal(new URL("https://www.google.com/calendar/ical/alex.guyon78%40gmail.com/public/basic.ics"));
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
					
					System.out.println(tomorrowMorning.getTime());
					System.out.println(tomorrowNight.getTime());
					
					ComponentList lc = this.c.get(tomorrowMorning.getTime(), tomorrowNight.getTime());
					
					if(lc.size() == 0)
						System.out.println("No events");
					else
						System.out.println(lc);
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		});
		this.jcron.start();
	}

	/**
	 * Stop the server
	 */
	public void stop(){
		this.server.setRunning(false);
		
		try {
			this.mainThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.jcron.stop();
		this.mqtt.disconnect();
		
	}

	/**
	 * Add ring in the crontab with the corresponding date
	 * @param c The date to ring
	 */
	public void addRing(Calendar c){
		String[] broker = this.config.getMQTTServer().split(":");
		ProcessTask p = new ProcessTask("echo Ring | nc " + broker[1].substring(2) + " " + broker[2] + " #smart");

		int min = c.get(Calendar.MINUTE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int d = c.get(Calendar.DAY_OF_MONTH);
		int m = c.get(Calendar.MONTH) + 1;

		this.cron.add(new SchedulingPattern(min +" " + hour + " " + d + " " + m + " *" ), p);

		this.cron.apply();
	}

	/**
	 * Get the configuration of the application loaded
	 * @return The configuration loaded.
	 */
	public Conf getConfig() {
		return this.config;
	}

	/**
	 * Set to the controller a special configuration
	 * @param config The configuration to set to the controller.
	 */
	public void setConfig(Conf config) {
		this.config = config;
	}
}
