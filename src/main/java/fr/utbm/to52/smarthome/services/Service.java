/**
 * 
 */
package fr.utbm.to52.smarthome.services;

import fr.utbm.to52.smarthome.controller.Conf;

/**
 * Define how service mean to be used
 * 
 * @author Alexandre Guyon
 *
 */
public interface Service {

	/**
	 * Setup the service
	 * @param c Config to set up
	 */
	public void setUp(Conf c);
	
	/**
	 * Start the service
	 */
	public void start();
	
	/**
	 * Stop the service
	 */
	public void stop();
	
}
