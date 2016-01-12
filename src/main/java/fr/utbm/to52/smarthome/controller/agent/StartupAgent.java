/**
 * 
 */
package fr.utbm.to52.smarthome.controller.agent;

import madkit.kernel.Agent;

/**
 * @author Alexandre Guyon
 *
 */
public class StartupAgent extends Agent{
	
	/**
	 * Startup agent reference
	 */
	public static StartupAgent startup;
	
	/**
	 * Initialize all agents
	 */
	public StartupAgent() {
		StartupAgent.startup = this;
	}
	
	@Override
	protected void activate() {
		this.logger.info("Startup Agent started");
	}
	
	@Override
	protected void live() {
		this.logger.info("Launch others agents");
		this.launchAgent("fr.utbm.to52.smarthome.controller.agent.Light");
		this.launchAgent("fr.utbm.to52.smarthome.controller.agent.Clock");
		this.launchAgent("fr.utbm.to52.smarthome.controller.agent.Bed");
		this.launchAgent("fr.utbm.to52.smarthome.controller.agent.Motion");
		this.launchAgent("fr.utbm.to52.smarthome.controller.agent.Blind");
	}
	
	@Override
	protected void end() {
		this.logger.info("Startup Agend ended");
	}
	
}
