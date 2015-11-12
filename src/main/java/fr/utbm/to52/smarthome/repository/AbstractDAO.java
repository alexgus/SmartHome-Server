/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import org.lightcouch.CouchDbClient;

/**
 * @author Alexandre Guyon
 * @param <D> Data to handle in DAO
 *
 */
public abstract class AbstractDAO<D> implements DAO<D> {
	
	/**
	 * Configured hibernate session
	 */
	protected CouchDbClient couch;
	
	public void setUp(CouchDbClient s){
		this.couch = s;
	}

}
