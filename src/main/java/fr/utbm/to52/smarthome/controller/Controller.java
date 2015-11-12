package fr.utbm.to52.smarthome.controller;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.to52.smarthome.controller.events.AddNoteEvent;
import fr.utbm.to52.smarthome.controller.events.AddRingEvent;
import fr.utbm.to52.smarthome.controller.events.GetLogBookEvent;
import fr.utbm.to52.smarthome.controller.events.GetNoteEvent;
import fr.utbm.to52.smarthome.controller.events.LightEvent;
import fr.utbm.to52.smarthome.controller.events.NoSuchCommand;
import fr.utbm.to52.smarthome.controller.events.QuitEvent;
import fr.utbm.to52.smarthome.controller.events.RingEvent;
import fr.utbm.to52.smarthome.services.AbstractService;
import fr.utbm.to52.smarthome.services.Service;
import fr.utbm.to52.smarthome.services.clock.ClockService;
import fr.utbm.to52.smarthome.services.com.CmdServer;
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
	
	private CouchdbService couch;
	
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
		
		this.couch = new CouchdbService();
		this.lService.add(this.couch);
	}

	@Override
	public void start(){ // FIXME DAO is a specific service of hibernate
		for (Service service : this.lService) {
			service.setUp(this.config);
			service.start();
		}
		
		this.enableEvent();
	}
	
	private void enableEvent(){
		this.cmdHandler.setQuitEvent(new QuitEvent(this.couch.getSession()));
		this.cmdHandler.setNoSuchCommand(new NoSuchCommand(this.couch.getSession()));
		this.cmdHandler.setRingEventController(new RingEvent(this.couch.getSession(), this.MQTT.getMqtt()));
		this.cmdHandler.setAddRingEventController(new AddRingEvent(this.couch.getSession(), this.clock.getCron()));
		this.cmdHandler.setLightEvent(new LightEvent(this.couch.getSession(), this.MQTT.getMqtt()));
		this.cmdHandler.setAddNote(new AddNoteEvent(this.couch.getSession(), this.couch.getNoteDao()));
		this.cmdHandler.setGetNote(new GetNoteEvent(this.couch.getSession(), this.couch.getNoteDao(), this.MQTT.getMqtt()));
		this.cmdHandler.setGetLogBook(new GetLogBookEvent(this.couch.getSession(), this.couch.getLogbookDAO(), this.MQTT.getMqtt()));
	}
	
	@Override
	public void stop() {
		for (Service service : this.lService)
			service.stop();
	}

}
