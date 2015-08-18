/**
 * 
 */
package fr.utbm.to52.smarthome.model.cron;

/**
 * @author Alexandre Guyon
 *
 */
public class JavaCron extends RingCron {

	/**
	 * @see fr.utbm.to52.smarthome.model.cron.Cron#Cron()
	 */
	public JavaCron() {
		super();
	}

	/**
	 * @see fr.utbm.to52.smarthome.model.cron.Cron#Cron(String)
	 * @param user @see fr.utbm.to52.smarthome.model.cron.Cron#Cron(String) 
	 */
	public JavaCron(String user) {
		super(user);
	}

	@Override
	protected void apply() {
		// Not needed here
	}
}
