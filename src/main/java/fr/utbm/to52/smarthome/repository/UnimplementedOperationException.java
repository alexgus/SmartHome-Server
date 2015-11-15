/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

/**
 * @author Alexandre Guyon
 *
 */
public class UnimplementedOperationException extends Exception {
	
	private static final long serialVersionUID = 7304415934833775299L;

	/**
	 * @param message Exception message
	 */
	public UnimplementedOperationException(String message) {
		super(message);
	}

}
