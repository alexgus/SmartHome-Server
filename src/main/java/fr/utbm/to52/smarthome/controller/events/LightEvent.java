/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import java.util.Timer;
import java.util.TimerTask;

import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.controller.Conf;
import fr.utbm.to52.smarthome.controller.events.core.AbstractEvent;
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

	private int nbCall = 0;
	
	/**
	 * Create lightEvent for increasing intensity of the light smoothly 
	 * @param s couchdb session
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
		if(this.nbCall == 0){
			this.nbCall++;
			this.clean();
			
			this.registerEvent(getClass(), o);
		
			TimerTask lightIncrease = new TimerTask() {

				@Override
				public void run() {
					LightEvent.this.getConnection().publish(Conf.getInstance().getLightTopic(), 
							Integer.toString(LightEvent.this.getLightIntensity()));
					LightEvent.this.setLightIntensity(
							Math.round(LightEvent.this.getLightIntensity() + LightEvent.this.step));
				}
			};
			this.schedLight.scheduleAtFixedRate(lightIncrease, 0, this.rate);
			
			this.scheduleCancel();
		}
	}
	
	private void clean(){
		this.schedLight = new Timer();
		this.lightIntensity = 0;
	}

	private void scheduleCancel() {
		Timer cancelTask = new Timer();
		TimerTask t = new TimerTask() {

			@Override
			public void run() {
				LightEvent.this.getLightScheduler().cancel();
				LightEvent.this.setNbCall(LightEvent.this.getNbCall() - 1);
			}
		};
		cancelTask.schedule(t, this.rate * ((this.maxIntesity + 1)/this.step));
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

	/**
	 * @return the nbCall
	 */
	public int getNbCall() {
		return this.nbCall;
	}

	/**
	 * @param nbCall the nbCall to set
	 */
	public void setNbCall(int nbCall) {
		this.nbCall = nbCall;
	}

}
