package fr.utbm.to52.smarthome.controller;

import java.io.IOException;

import fr.utbm.to52.smarthome.model.Cron;

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
		
		this.cron = new Cron();
		System.out.println(this.cron.getCrontab().toString());
	}

	/**
	 * Start the server
	 */
	public void start(){
		
		this.connection.connect();
		
		try {
			this.server = new SocketInput(2000);
			this.server.setRingEventController(new RingEvent(this.connection));
			
			
			this.mainThread = new Thread(this.server);
			this.mainThread.start();
			
			this.mainThread.join();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
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
