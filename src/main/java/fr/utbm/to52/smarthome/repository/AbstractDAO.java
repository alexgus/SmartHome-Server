/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import org.hibernate.ogm.OgmSession;

/**
 * @author Alexandre Guyon
 * @param <D> Data to handle in DAO
 *
 */
public abstract class AbstractDAO<D> implements DAO<D> {
	
	/**
	 * Configured hibernate session
	 */
	protected OgmSession hbmSess;
	
	public void setUp(OgmSession s){
		this.hbmSess = s;
	}

}
