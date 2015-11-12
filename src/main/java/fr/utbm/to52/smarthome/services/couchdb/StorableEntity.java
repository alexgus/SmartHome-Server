/**
 * 
 */
package fr.utbm.to52.smarthome.services.couchdb;

/**
 * @author Alexandre Guyon
 *
 */
public abstract class StorableEntity {

	/**
	 * Table name
	 */
    protected final String $table;
	
	/**
	 * Constructor defining table property
	 * @param table Table to store
	 */
	public StorableEntity(String table) {
		this.$table = table;
	}

}
