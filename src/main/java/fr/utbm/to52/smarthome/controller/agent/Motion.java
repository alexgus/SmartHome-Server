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
public class Motion extends ComponentAgent {

	@Override
	protected void activate() {
		this.logger.info("Motion started");
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
		this.logger.info("Motion ended");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void inform(Message m) {
		if(m.getSubject().equals(Conf.getInstance().getMotionTopic())){
			this.logger.info(m.getMessage());
			this.publish(Conf.getInstance().getLightTopic(), "off");
		}
	}

}
