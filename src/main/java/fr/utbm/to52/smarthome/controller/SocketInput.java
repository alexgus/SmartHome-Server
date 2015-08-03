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
 * @author alexandre
 *
 */
public class SocketInput implements Runnable{

	private ServerSocket server;

	private List<Socket> listSocket;

	private Event ringEvent;

	private boolean running;

	public SocketInput(int port) throws IOException{
		this.setListSocket(new LinkedList<Socket>());
		this.server = new ServerSocket(port);
	}

	public void setRingEventController(Event r){
		this.ringEvent = r;
	}

	@Override
	public void run() {
		this.running =true;
		while(this.running){
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
							if(command.equals("Ring")){
								if(SocketInput.this.ringEvent != null)
									SocketInput.this.ringEvent.inform(null);
							}else if(command.equals("Quit")){
								SocketInput.this.running = false;
							}else{
								System.out.println("unhandled event : " + command);
							}

							this.s.close();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				};

				Thread t = new Thread(r);
				t.start();

			}
		}
	}

	public List<Socket> getListSocket() {
		return this.listSocket;
	}

	public void setListSocket(List<Socket> listSocket) {
		this.listSocket = listSocket;
	}

}
