package fr.utbm.to52.smarthome.controller;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.to52.smarthome.events.AddRingEvent;
import fr.utbm.to52.smarthome.events.LightEvent;
import fr.utbm.to52.smarthome.events.RingEvent;
import fr.utbm.to52.smarthome.services.AbstractService;
import fr.utbm.to52.smarthome.services.Service;
import fr.utbm.to52.smarthome.services.clock.ClockService;
import fr.utbm.to52.smarthome.services.com.CmdServer;
import fr.utbm.to52.smarthome.services.com.MQTTService;
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

	private CommandHandlerImpl cmdHandler;
	
	private List<Service> lService;
	
	// Services
	
	private MQTTService MQTT;

	private CmdServer server;

	private ClockService clock;
	
	private GmailService mail;
	
	private Controller(){		
		this.cmdHandler = new CommandHandlerImpl();
		
		this.lService = new ArrayList<>();
		
		this.MQTT = new MQTTService(this.cmdHandler);
		this.lService.add(this.MQTT);
		
		this.server = new CmdServer(this.cmdHandler);
		this.lService.add(this.server);
		
		this.clock = new ClockService();
		this.lService.add(this.clock);
		
		this.mail = new GmailService();
		this.lService.add(this.mail);
	}

	@Override
	public void start(){
		for (Service service : this.lService) {
			service.setUp(this.config);
			service.start();
		}
		
		this.enableEvent();
	}
	
	private void enableEvent(){
		this.cmdHandler.setRingEventController(new RingEvent(this.MQTT.getMqtt()));
		this.cmdHandler.setAddRingEventController(new AddRingEvent(this.clock.getCron()));
		this.cmdHandler.setLightEvent(new LightEvent(this.MQTT.getMqtt()));
	}
	
	@Override
	public void stop() {
		for (Service service : this.lService)
			service.stop();
	}

}
