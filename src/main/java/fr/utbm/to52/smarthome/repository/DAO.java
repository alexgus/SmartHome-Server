/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import java.util.List;

import org.json.JSONObject;
import org.lightcouch.CouchDbClient;

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
	 * Get all the data from this type
	 * @return Collection of data
	 */
	public List<D> getData();
	
	/**
	 * Get all the data from this type with this criteria (aspect)
	 * @param criteria JSON criteria
	 * @return Collection of data
	 */
	public List<D> getData(JSONObject criteria);
	
	/**
	 * Set up access to hibernate session
	 * @param s Opened session to hibernate
	 */
	public void setUp(CouchDbClient s);
	
}
