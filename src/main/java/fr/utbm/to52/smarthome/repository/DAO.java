/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import org.hibernate.Session;

/**
 * 
 * @author Alexandre Guyon
 * @param <D> Data to save
 */
public interface DAO<D>{

	/**
	 * @param data Save data D
	 */
	public void save(D data);
	
	/**
	 * Set up access to hibernate session
	 * @param s Opened session to hibernate
	 */
	public void setUp(Session s);
	
}
