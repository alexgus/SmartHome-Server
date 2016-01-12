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
	 * @param subject The subject of the message
	 * @param cmd The command to handle
	 */
	public void handle(String subject, String cmd);
	
}
