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
		Conf c = new Conf();
		c.importConf();
		Controller.getInstance().setUp(c);
		Controller.getInstance().start();
	}
}
