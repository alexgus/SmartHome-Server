/**
 * 
 */
package fr.utbm.to52.smarthome.controller;

/**
 * @author Alexandre Guyon
 *
 */
public class Message {
	
	private String subject;
	
	private String message;
	
	/**
	 * @param subject Message's subject
	 * @param message Message
	 */
	public Message(String subject, String message) {
		this.message = message;
		this.subject = subject;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}
}
