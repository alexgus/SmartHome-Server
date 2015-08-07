package fr.utbm.to52.smarthome.controller;

import java.io.IOException;
import java.util.Calendar;

import fr.utbm.to52.smarthome.events.AddRingEvent;
import fr.utbm.to52.smarthome.events.RingEvent;
import fr.utbm.to52.smarthome.model.Cron;
import fr.utbm.to52.smarthome.network.MQTT;
import fr.utbm.to52.smarthome.network.SocketInput;
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

	private MQTT mqtt;

	private Conf config;

	private Cron cron;

	private CommandHandlerImpl cmdHandler;

	private Controller(){
		this.config = new Conf();
		this.config.importConf();
		this.cmdHandler = new CommandHandlerImpl();
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

		this.loadICal();
		this.loadGCal();
		this.loadCalDav();

		this.stop();
	}

	private void loadGCal() {
		// https://developers.google.com/google-apps/calendar/quickstart/java
		// https://developers.google.com/resources/api-libraries/documentation/calendar/v3/java/latest/
	}

	private void loadCalDav(){
		// http://build.mnode.org/projects/ical4j-connector/apidocs/
		// http://blogs.nologin.es/rickyepoderi/index.php?/archives/14-Introducing-CalDAV-Part-I.html
		// http://blogs.nologin.es/rickyepoderi/index.php?/archives/15-Introducing-CalDAV-Part-II.html
		// http://blogs.nologin.es/rickyepoderi/uploads/IntroducingCalDAVPartII/sample.caldav.xml.zip


		/*HttpClient client = new HttpClient(); 
		
		OptionsMethod options = new OptionsMethod("https://www.google.com/calendar/dav/alex.guyon78@gmail.com/events/");
		try {
			client.executeMethod(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(options.getStatusLine());
		for (int i = 0; i < options.getResponseHeaders().length; i++) {
			System.out.println(options.getResponseHeaders()[i].getName() + ": "
					+ options.getResponseHeaders()[i].getValue());
		}*/
	}

	private void loadICal() {
		// http://build.mnode.org/projects/ical4j/apidocs/
		/*try {
			net.fortuna.ical4j.model.Calendar ical = Calendars.load(new URL("https://www.google.com/calendar/ical/alex.guyon78%40gmail.com/private-c99c6c281cf94c48e85f2155434f0423/basic.ics"));
			System.out.println("finish loaded ical");
		} catch (IOException | ParserException e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Stop the server
	 */
	public void stopServer(){
		this.server.setRunning(false);
	}

	/**
	 * Stop the controller and so the app
	 */
	public void stop(){
		try {
			this.mainThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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
