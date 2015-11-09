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
	 * @param port The next port choosen
	 */
	public NextPortException(int port) {
		this.port = port;
	}

	/**
	 * @param message message of the exception
	 * @param port The next port chosen
	 */
	public NextPortException(String message, int port) {
		super(message);
		this.port = port;
	}

	/**
	 * @param cause cause of the exception
	 * @param port The next port chosen
	 */
	public NextPortException(Throwable cause, int port) {
		super(cause);
		this.port = port;
	}

	/**
	 * @param message message of the exception
	 * @param cause cause of the exception
	 * @param port The next port chosen 
	 */
	public NextPortException(String message, Throwable cause, int port) {
		super(message, cause);
		this.port = port;
	}

	/**
	 * @param message message of the exception
	 * @param cause cause of the exception
	 * @param enableSuppression ?
	 * @param writableStackTrace ?
	 * @param port The next port chosen
	 */
	public NextPortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int port) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.port = port;
	}
	
	/**
	 * @return The next port chosen
	 */
	public int getPort(){
		return this.port;
	}

}
