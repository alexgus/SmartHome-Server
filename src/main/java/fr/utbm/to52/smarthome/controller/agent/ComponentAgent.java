/**
 * 
 */
package fr.utbm.to52.smarthome.controller.agent;

import madkit.kernel.Agent;

/**
 * @author Alexandre Guyon
 *
 */
public class ComponentAgent extends Agent {
	
	@Override
	protected void activate() {
		this.logger.info("ComponenentAgent : activate");
	}
	
	@Override
	protected void live() {
		this.logger.info("ComponenentAgent : live");
	}
	
	@Override
	protected void end() {
		this.logger.info("ComponenentAgent : end");
	}
	
}
