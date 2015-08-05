/**
 * 
 */
package fr.utbm.to52.smarthome.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to handle TCP connection.
 * Command can be registered and then events are 
 * triggered when they arrived for launching methods 
 * 
 * @author Alexandre Guyon
 *
 */
public class SocketInput implements Runnable{

	private ServerSocket server;

	private List<Socket> listSocket;

	private Event ringEvent;

	private boolean running;

	/**
	 * Default constructor. Launch server on desired port.</br>
	 * For receiving event you need to register callback !
	 * @param port Port you want to open the server on
	 * @throws IOException Throw IO Exception when error happens
	 */
	public SocketInput(int port) throws IOException{
		this.setListSocket(new LinkedList<Socket>());
		this.server = new ServerSocket(port);
	}

	@Override
	public void run() {
		this.setRunning(true);
		while(this.getRunning()){
			try {
				this.getListSocket().add(this.server.accept());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			synchronized (this) {
				Runnable r = new Runnable() {

					private Socket s;

					@Override
					public void run() {
						this.s = SocketInput.this.getListSocket().get(SocketInput.this.getListSocket().size() - 1);
						BufferedReader reader;
						try {
							reader = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
							String command = reader.readLine();
							if(command.equals(Controller.getInstance().getConfig().getServerCommandRing())){
								if(SocketInput.this.getRingEvent() != null)
									SocketInput.this.getRingEvent().inform(null);
							}else if(command.equals(Controller.getInstance().getConfig().getServerCommandQuit())){
								SocketInput.this.setRunning(false);
							}else{
								System.out.println("unhandled command : " + command);
							}

							this.s.close();
							SocketInput.this.getListSocket().remove(this.s);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				};

				Thread t = new Thread(r);
				t.start();	
				
				// TODO Ugly, not multi user
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Set the ring event 
	 * @param r The ring event
	 */
	public void setRingEventController(Event r){
		this.ringEvent = r;
	}
	
	Event getRingEvent(){
		return this.ringEvent;
	}
	
	void setRunning(boolean b) {
		this.running = b;
	}
	
	boolean getRunning(){
		return this.running;
	}

	List<Socket> getListSocket() {
		return this.listSocket;
	}

	void setListSocket(List<Socket> listSocket) {
		this.listSocket = listSocket;
	}

}
