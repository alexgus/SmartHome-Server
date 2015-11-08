/**
 * 
 */
package fr.utbm.to52.smarthome.services.com;

import java.io.IOException;

import fr.utbm.to52.smarthome.controller.CommandHandler;
import fr.utbm.to52.smarthome.services.AbstractService;

/**
 * @author Alexandre Guyon
 *
 */
public class CmdServer extends AbstractService{

	private SocketInput server;

	private Thread mainThread;

	private CommandHandler cmdHandler;

	/**
	 * Constructor taking a command handler for execute commands
	 * @param c The command handler
	 */
	public CmdServer(CommandHandler c) {
		this.cmdHandler = c;
	}

	@Override
	public void start() {
		try {
			this.server = new SocketInput(this.config.getServerPort());
			this.server.setCmdHandler(this.cmdHandler);

			this.mainThread = new Thread(this.server);
			this.mainThread.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		this.server.setRunning(false);
		
		try {
			this.mainThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
