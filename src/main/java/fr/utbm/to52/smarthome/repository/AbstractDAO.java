/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import org.hibernate.Session;

/**
 * @author Alexandre Guyon
 * @param <D> Data to handle in DAO
 *
 */
public abstract class AbstractDAO<D> implements DAO<D> {
	
	/**
	 * Configured hibernate session
	 */
	protected Session hbmSess;
	
	public void setUp(Session s){
		this.hbmSess = s;
	}

}
