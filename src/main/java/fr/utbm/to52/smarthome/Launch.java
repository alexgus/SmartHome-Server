package fr.utbm.to52.smarthome;

import fr.utbm.to52.smarthome.controller.Conf;
import fr.utbm.to52.smarthome.controller.Controller;

/**
 * 
 * @author Alexandre Guyon
 *
 */
public class Launch {

	/**
	 * Main entry point. This is currently in development. It launch the main controller.
	 * @param args Nothing in args
	 */
	public static void main(String[] args){
		Conf c = Conf.getInstance();
		c.importConf();
		Controller cont = new Controller();
		cont.setUp(c);
		cont.start();
	}
}
