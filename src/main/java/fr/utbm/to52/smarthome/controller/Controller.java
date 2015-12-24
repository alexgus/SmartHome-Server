package fr.utbm.to52.smarthome.controller;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.to52.smarthome.services.AbstractService;
import fr.utbm.to52.smarthome.services.Service;
import fr.utbm.to52.smarthome.services.clock.ClockService;
import fr.utbm.to52.smarthome.services.clock.CmdServer;
import fr.utbm.to52.smarthome.services.com.MQTTService;
import fr.utbm.to52.smarthome.services.couchdb.CouchdbService;
import fr.utbm.to52.smarthome.services.mail.GmailService;


/**
 * This controller is a singleton. For getting an instance from it 
 * <code>Controller.getInstance()</code> This initialize it with 
 * the default configuration file if it was not created before.
 * 
 * @author Alexandre Guyon
 *
 */
public class Controller extends AbstractService{

	private boolean running;
	
	private CommandHandlerImpl cmdHandler;
	
	private Thread tCmdhandler;
	
	private List<Service> lService;
	
	// Services
	
	private MQTTService MQTT;

	private CmdServer server;

	private ClockService clock;
	
	private GmailService mail;
	
	private CouchdbService couch;
	
	/**
	 * Default instance of a controller
	 */
	public Controller(){
		
		this.cmdHandler = new CommandHandlerImpl(this);
		this.tCmdhandler = new Thread(this.cmdHandler);
		
		this.lService = new ArrayList<>();
		
		this.MQTT = new MQTTService(this.cmdHandler);
		this.lService.add(this.MQTT);
		
		this.couch = new CouchdbService();
		this.lService.add(this.couch);
		
		if(this.config.getFeaturesEnabled().contains("clock")){
			this.server = new CmdServer(this.cmdHandler);
			this.lService.add(this.server);
			
			this.clock = new ClockService();
			this.lService.add(this.clock);
		}
		
		/*this.mail = new GmailService();
		this.lService.add(this.mail);*/
	}

	@Override
	public void start(){
		for (Service service : this.lService) {
			service.setUp(this.config);
			service.start();
		}
		
		this.enableEvent();
		this.tCmdhandler.start();
		
		this.running = true;
	}
	
	private void enableEvent(){ // TODO make DAO singleton
		
	}
	
	@Override
	public void stop() {
		this.running = false;
		for (Service service : this.lService)
			service.stop();
		
		try{
			this.tCmdhandler.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}

	/**
	 * @return the running
	 */
	public boolean isRunning() {
		return this.running;
	}
	
	/**
	 * @return The list of services
	 */
	public List<Service> getServices(){
		return this.lService;
	}
	
	/**
	 * @param c Services class
	 * @return Asked service
	 */
	public Service getService(Class<? extends Service> c){
		for (Service service : this.lService) {
			if(service.getClass() == c)
				return service;
		}
		return null;
	}
}
