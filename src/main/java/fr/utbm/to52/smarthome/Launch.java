package fr.utbm.to52.smarthome;

import java.io.IOException;

import fr.utbm.to52.smarthome.controller.RingEvent;
import fr.utbm.to52.smarthome.controller.SocketInput;

public class Launch {

	public static void main(String[] args){
		System.out.println("in main !");
		
		SocketInput server;
		Thread t;
		
		try {
			server = new SocketInput(2000);
			server.setRingEventController(new RingEvent());
			
			
			t = new Thread(server);
			t.start();
			
			t.join();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("end");
		System.out.flush();
	}
}
