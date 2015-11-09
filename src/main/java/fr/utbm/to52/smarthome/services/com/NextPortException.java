/**
 * 
 */
package fr.utbm.to52.smarthome.services.com;

/**
 * @author Alexandre Guyon
 *
 */
public class NextPortException extends Exception {

	private static final long serialVersionUID = -4990252864895899289L;
	
	/**
	 * Server open on this port
	 */
	private int port;
	
	/**
	 * 
	 */
	public NextPortException(int port) {
		this.port = port;
	}

	/**
	 * @param message
	 */
	public NextPortException(String message, int port) {
		super(message);
		this.port = port;
	}

	/**
	 * @param cause
	 */
	public NextPortException(Throwable cause, int port) {
		super(cause);
		this.port = port;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NextPortException(String message, Throwable cause, int port) {
		super(message, cause);
		this.port = port;
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NextPortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int port) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.port = port;
	}
	
	public int getPort(){
		return this.port;
	}

}
