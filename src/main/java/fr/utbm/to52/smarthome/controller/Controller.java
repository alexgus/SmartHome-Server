package fr.utbm.to52.smarthome.controller;

import java.io.IOException;

public class Controller {
	
	private SocketInput server;
	private Thread mainThread;
	
	public Controller(){
		
	}
	
	public void start(){	
		
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
	
}
