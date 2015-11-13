/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import org.lightcouch.CouchDbClient;
import org.lightcouch.View;

import fr.utbm.to52.smarthome.util.BasicIO;

/**
 * @author Alexandre Guyon
 * @param <D> Data to handle in DAO
 *
 */
public abstract class AbstractDAO<D> implements DAO<D> {
	
	/**
	 * designDoc directory
	 */
	protected String designDoc;
	
	/**
	 * Configured hibernate session
	 */
	protected CouchDbClient couch;
	
	public void setUp(CouchDbClient s){
		String classname = this.getClass()
				.getSimpleName()
				.toLowerCase();
		
		this.designDoc = classname.substring(0, classname.indexOf("dao"));
		
		this.couch = s;
		s.design().synchronizeWithDb(s.design().getFromDesk(this.designDoc));
	}
	
	/**
	 * Get string from view
	 * @param v The view to query
	 * @return the string result from database
	 */
	protected static String getStringFromView(View v){
		return BasicIO.getStringFromInput(v.queryForStream());
	}

}
