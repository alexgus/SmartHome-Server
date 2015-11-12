/**
 * 
 */
package fr.utbm.to52.smarthome.model.location;

import fr.utbm.to52.smarthome.services.couchdb.StorableEntity;

/**
 * @author Alexandre Guyon
 *
 */
public class Location extends StorableEntity{
	
	private String loc;
	
	/**
	 * Default constructor
	 */
	public Location() {
		super(Location.class.getName());
	}

	/**
	 * @return the loc
	 */
	public String getLoc() {
		return this.loc;
	}

	/**
	 * @param loc the loc to set
	 */
	public void setLoc(String loc) {
		this.loc = loc;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.loc == null) ? 0 : this.loc.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (this.loc == null) {
			if (other.loc != null)
				return false;
		} else if (!this.loc.equals(other.loc))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Location [loc=" + this.loc + "]";
	}
}
