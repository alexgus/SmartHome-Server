package fr.utbm.to52.smarthome.controller;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.to52.smarthome.controller.events.AbortEvent;
import fr.utbm.to52.smarthome.controller.events.AddRing;
import fr.utbm.to52.smarthome.controller.events.Light;
import fr.utbm.to52.smarthome.controller.events.MotionSensor;
import fr.utbm.to52.smarthome.controller.events.NoSuchCommand;
import fr.utbm.to52.smarthome.controller.events.PresenceEvent;
import fr.utbm.to52.smarthome.controller.events.QuitEvent;
import fr.utbm.to52.smarthome.controller.events.Ring;
import fr.utbm.to52.smarthome.controller.events.ShutterEvent;
import fr.utbm.to52.smarthome.controller.events.core.Event;
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
	 * @param c Configuration of the app
	 */
	public Controller(Conf c){
		
		this.cmdHandler = new CommandHandlerImpl(this);
		this.tCmdhandler = new Thread(this.cmdHandler);
		
		this.lService = new ArrayList<>();
		
		this.MQTT = new MQTTService(this.cmdHandler);
		this.lService.add(this.MQTT);
		
		this.couch = new CouchdbService();
		this.lService.add(this.couch);
		
		if(c.getFeaturesEnabled().contains("clock")){
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
		this.cmdHandler.setQuitEvent(new QuitEvent(this, this.couch.getSession()));
		this.cmdHandler.setNoSuchCommand(new NoSuchCommand(this.couch.getSession()));
		
		this.cmdHandler.setAddRingEventController(new AddRing(this.couch.getSession(), this.clock.getCron()));
		
		this.cmdHandler.setRingEventController(new Ring(this.couch.getSession(), this.MQTT.getMqtt()));
		this.cmdHandler.setLightEvent(new Light(this.couch.getSession(), this.MQTT.getMqtt()));
		
		this.cmdHandler.setMotionSensor(new MotionSensor(this.couch.getSession()));
		this.cmdHandler.setAbort(new AbortEvent(this.couch.getSession(), this.MQTT.getMqtt()));
		this.cmdHandler.setShutter(new ShutterEvent(this.couch.getSession(), this.MQTT.getMqtt()));
		
		this.cmdHandler.setPresence(new PresenceEvent(this.couch.getSession(), this.MQTT.getMqtt()));		
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
