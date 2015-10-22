/**
 * 
 */
package fr.utbm.to52.smarthome.controller.cronTask;

/**
 * @author Alexandre Guyon
 *
 */
public class MailCheck implements Runnable {

	/**
	 * 
	 */
	public MailCheck() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("Running mail check");
	}

}
