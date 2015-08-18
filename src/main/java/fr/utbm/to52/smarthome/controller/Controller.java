package fr.utbm.to52.smarthome.controller;

import java.io.IOException;

import fr.utbm.to52.smarthome.events.AddRingEvent;
import fr.utbm.to52.smarthome.events.RingEvent;
import fr.utbm.to52.smarthome.model.cron.JavaCron;
import fr.utbm.to52.smarthome.model.cron.RingCron;
import fr.utbm.to52.smarthome.model.cron.SystemCron;
import fr.utbm.to52.smarthome.network.MQTT;
import fr.utbm.to52.smarthome.network.SocketInput;
import it.sauronsoftware.cron4j.Scheduler;

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
	
	/**
	 * Defining the source of the command is from network. tag configuration
	 */
	public static final int SOURCE_NET = 0;
	
	/**
	 * Defining the source of the command is from ICAL. tagICal configuration
	 */
	public static final int SOURCE_ICAL = 1;

	private SocketInput server;
	
	private Thread mainThread;

	private MQTT mqtt;

	private Conf config;

	private RingCron cron;

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
		this.confCron();

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
		
		this.jcron.start();
	}

	private void confCron() {
		if(Controller.getInstance().getConfig().getCronSource().equals(Conf.CRON_SYSTEM))
			this.cron = new SystemCron();
		else
			this.cron = new JavaCron();
		
		for(int i = 0 ; i < this.getConfig().getAlarmURL().size() ; ++i)
			this.jcron.schedule("* * * * *", new DateController(i));
		
		this.jcron.schedule("0 * * * *", new CronCleaner(this.getCron()));
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

	/**
	 * @return the jcron
	 */
	public Scheduler getJcron() {
		return this.jcron;
	}

	/**
	 * @param jcron the jcron to set
	 */
	public void setJcron(Scheduler jcron) {
		this.jcron = jcron;
	}
}
