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
public class Bed extends ComponentAgent {

	@Override
	protected void activate() {
		this.logger.info("Bed started");
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
		this.logger.info("Bed ended");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void inform(Message m) {
		if(m.getSubject().equals(Conf.getInstance().getBedTopic())){
			this.logger.info(m.getMessage());
			this.publish(Conf.getInstance().getLightTopic(), "on");
			this.publish(Conf.getInstance().getClockTopic(), Conf.getInstance().getClockAbort());
			this.publish(Conf.getInstance().getBlindTopic(), Conf.getInstance().getBlindOpen());
		}
	}

}
