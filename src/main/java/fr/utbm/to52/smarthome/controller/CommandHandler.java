/**
 * 
 */
package fr.utbm.to52.smarthome.controller;

/**
 * Define how to hanlde commands
 * @author Alexandre Guyon
 *
 */
public interface CommandHandler {

	/**
	 * Handle the command cmd
	 * @param cmd The command to handle
	 */
	public void handle(String cmd);
	
}
