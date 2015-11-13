/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import java.util.Timer;
import java.util.TimerTask;

import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.controller.Conf;
import fr.utbm.to52.smarthome.services.com.MQTT;

/**
 * @author Alexandre Guyon
 *
 */
public class LightEvent extends AbstractEvent {

	private MQTT connection; 

	private final int maxIntesity = 150;
	private final long rate = 1000;
	private final long step = 10;

	private int lightIntensity = 0; 

	private Timer schedLight = new Timer();
	
	/**
	 * Create lightEvent for increasing intensity of the light smoothly 
	 * @param s Hibernate session
	 * @param c The MQTT connection to use
	 */
	public LightEvent(CouchDbClient s, MQTT c) {
		super(s);
		this.connection = c;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.events.Event#inform(java.lang.Object)
	 */
	@Override
	public void inform(Object o) {
		this.registerEvent(getClass(), o);
		
        TimerTask lightIncrease = new TimerTask() {
			
			@Override
			public void run() {
				LightEvent.this.getConnection().publish(Conf.getInstance().getMQTTLightTopic(), Integer.toString(LightEvent.this.getLightIntensity()));
				LightEvent.this.setLightIntensity(Math.round(LightEvent.this.getLightIntensity() + LightEvent.this.step));
			}
		};
		this.schedLight.scheduleAtFixedRate(lightIncrease, 0, this.rate); // FIXME When two call is done quickly --> Crash
		
		this.scheduleCancel();
	}

	private void scheduleCancel() {
		Timer cancelTask = new Timer();
		TimerTask t = new TimerTask() {
			
			@Override
			public void run() {
				LightEvent.this.getLightScheduler().cancel();
			}
		};
		cancelTask.schedule(t, this.rate * (this.maxIntesity/this.step));
	}

	/**
	 * @return Get the current light scheduler
	 */
	public Timer getLightScheduler(){
		return this.schedLight;
	}
	
	/**
	 * @param i Light intensity to set
	 */
	public void setLightIntensity(int i){
		this.lightIntensity = i;
	}
	
	/**
	 * @return The current light intensity
	 */
	public int getLightIntensity(){
		return this.lightIntensity;
	}
	
	/**
	 * @return The current MQTT conneciton
	 */
	public MQTT getConnection(){
		return this.connection;
	}
	
}
