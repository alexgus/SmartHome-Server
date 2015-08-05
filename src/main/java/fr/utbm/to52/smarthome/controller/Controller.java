package fr.utbm.to52.smarthome.controller;

import java.io.IOException;
import java.util.Calendar;

import fr.utbm.to52.smarthome.model.Cron;
import it.sauronsoftware.cron4j.ProcessTask;
import it.sauronsoftware.cron4j.SchedulingPattern;

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
	
	private MQTT connection;
	
	private Conf config;
	
	private Cron cron;
	
	private Controller(){
		this.config = new Conf();
		this.config.importConf();
		
		this.connection = new MQTT(this.config.getMQTTID(), this.config.getMQTTServer(), this.config.getMQTTQOS());
	}

	/**
	 * Start the server
	 */
	public void start(){
		this.cron = new Cron();
		this.connection.connect();
		
		this.addRing(Calendar.getInstance());
		
		try {
			this.server = new SocketInput(this.getConfig().getServerPort());
			this.server.setRingEventController(new RingEvent(this.connection));
			
			
			this.mainThread = new Thread(this.server);
			this.mainThread.start();
			
			this.mainThread.join();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
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
