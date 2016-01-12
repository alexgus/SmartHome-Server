/**
 * 
 */
package fr.utbm.to52.smarthome.services.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import fr.utbm.to52.smarthome.controller.CommandHandler;

/**
 * This class is used to handle TCP connection.
 * Command can be registered and then events are 
 * triggered when they arrived for launching methods 
 * 
 * @author Alexandre Guyon
 *
 */
public class SocketInput implements Runnable{

	/**
	 * Prefix subject sent by the socket
	 */
	public static String SUBJECT = "/internal";
	
	private ServerSocket server;

	private List<Socket> listSocket;

	private boolean running;
	
	private CommandHandler cmdHandler;

	/**
	 * Default constructor. Launch server on desired port.</br>
	 * For receiving event you need to register callback !
	 * @param port Port you want to open the server on
	 * @throws IOException Throw IO Exception when error happens
	 * @throws NextPortException Open server on next port if already in use
	 */
	public SocketInput(int port) throws IOException, NextPortException{
		this.setListSocket(new LinkedList<Socket>());
		try{
			this.server = new ServerSocket(port);
		}catch(java.net.BindException e){
			System.err.println("Server already launched. Launch it on the next port");
			this.server = new ServerSocket(port + 1);
			throw new NextPortException(port + 1);
		}
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
							
							SocketInput.this.getCmdHandler().handle(SocketInput.SUBJECT+this.s.getPort(), command);
							
							this.s.close();
							reader.close();
							SocketInput.this.getListSocket().remove(this.s);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				};

				Thread t = new Thread(r);
				t.start();	
				
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			this.server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set the state of the server
	 * @param b True running, false othewise
	 */
	public void setRunning(boolean b) {
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

	/**
	 * Get the command handler for this server
	 * @return The command handler
	 */
	public CommandHandler getCmdHandler() {
		return this.cmdHandler;
	}

	/**
	 * Set The command handler for this server
	 * @param cmdHandler The command handler to set
	 */
	public void setCmdHandler(CommandHandler cmdHandler) {
		this.cmdHandler = cmdHandler;
	}

}
