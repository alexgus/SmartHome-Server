/**
 * 
 */
package fr.utbm.to52.smarthome.controller.agent;

import fr.utbm.to52.smarthome.controller.Conf;
import fr.utbm.to52.smarthome.controller.Message;

/**
 * @author Alexandre Guyon
 *
 */
public class LightAgent extends ComponentAgent {

	@Override
	protected void activate() {
		this.logger.info("Light started");
		this.subscribe(Conf.getInstance().getLightTopic());
	}
	
	@Override
	protected void live() {
		while(true){
			// Test
		}
	}
	
	@Override
	protected void end() {
		this.logger.info("Light ended");
	}
	
	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.controller.agent.ComponentAgent#inform(fr.utbm.to52.smarthome.controller.Message)
	 */
	@Override
	protected void inform(Message m) {
		if(m.getSubject().equals(Conf.getInstance().getLightTopic())){
			this.logger.info(m.getMessage());
		}
	}

}
