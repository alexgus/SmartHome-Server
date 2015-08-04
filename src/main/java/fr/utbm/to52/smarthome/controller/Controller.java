package fr.utbm.to52.smarthome.controller;

import java.io.IOException;

public class Controller {
	
	private static Controller singleton = null;
	
	public static Controller getInstance(){
		if(singleton == null)
			singleton = new Controller();
		return singleton;
	}
	
	private SocketInput server;
	private Thread mainThread;
	
	private Conf config;
	
	public Controller(){
		this.config = new Conf();
	}

	public void start(){	
		this.config.importConf();
		
		try {
			this.server = new SocketInput(2000);
			this.server.setRingEventController(new RingEvent());
			
			
			this.mainThread = new Thread(this.server);
			this.mainThread.start();
			
			this.mainThread.join();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Conf getConfig() {
		return this.config;
	}

	public void setConfig(Conf config) {
		this.config = config;
	}
}
