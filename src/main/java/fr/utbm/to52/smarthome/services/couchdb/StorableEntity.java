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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.$table == null) ? 0 : this.$table.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StorableEntity other = (StorableEntity) obj;
		if (this.$table == null) {
			if (other.$table != null)
				return false;
		} else if (!this.$table.equals(other.$table))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StorableEntity [$table=" + this.$table + "]";
	}

}
