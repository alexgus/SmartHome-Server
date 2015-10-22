/**
 * 
 */
package fr.utbm.to52.smarthome.events;

import java.util.Timer;
import java.util.TimerTask;

import fr.utbm.to52.smarthome.controller.Conf;
import fr.utbm.to52.smarthome.controller.Controller;
import fr.utbm.to52.smarthome.network.MQTT;

/**
 * @author Alexandre Guyon
 *
 */
public class LightEvent implements Event {

	private MQTT connection; 

	private final int maxIntesity = 150;
	private final long rate = 1000;
	private final long step = 10;

	private int lightIntensity = 0; 

	private Timer schedLight = new Timer();
	
	/**
	 * Create lightEvent for increasing intensity of the light smoothly 
	 * @param c The MQTT connection to use
	 */
	public LightEvent(MQTT c) {
		this.connection = c;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.events.Event#inform(java.lang.Object)
	 */
	@Override
	public void inform(Object o) {
        TimerTask lightIncrease = new TimerTask() {
			
			@Override
			public void run() {
				LightEvent.this.connection.publish(Controller.getInstance().getConfig().getMQTTLightTopic(), Integer.toString(LightEvent.this.lightIntensity));
				LightEvent.this.lightIntensity += LightEvent.this.step;
			}
		};
		this.schedLight.scheduleAtFixedRate(lightIncrease, 0, this.rate);
		
		this.scheduleCancel();
	}

	private void scheduleCancel() {
		Timer cancelTask = new Timer();
		TimerTask t = new TimerTask() {
			
			@Override
			public void run() {
				LightEvent.this.schedLight.cancel();
			}
		};
		cancelTask.schedule(t, this.rate * (this.maxIntesity/this.step));
	}

}
