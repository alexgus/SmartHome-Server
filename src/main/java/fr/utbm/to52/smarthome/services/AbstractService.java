/**
 * 
 */
package fr.utbm.to52.smarthome.services;

import fr.utbm.to52.smarthome.controller.Conf;

// TODO add dependencies to other services

/**
 * @author Alexandre Guyon
 *
 */
public abstract class AbstractService implements Service {

	/**
	 * Configuration of the service
	 */
	protected Conf config;

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.services.Service#setUp(fr.utbm.to52.smarthome.controller.Conf)
	 */
	@Override
	public void setUp(Conf c) {
		this.config = c;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.services.Service#start()
	 */
	@Override
	public abstract void start();

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.services.Service#stop()
	 */
	@Override
	public abstract void stop();

	// FIXME Suppress this getter (add weakreference)
	/**
	 * @return the config
	 */
	public Conf getConfig() {
		return this.config;
	}
	
}
