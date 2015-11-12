/**
 * 
 */
package fr.utbm.to52.smarthome.model.location;

/**
 * @author Alexandre Guyon
 *
 */
public class Location {
	
    private String revision;
	
	private String loc;
	
	/**
	 * 
	 */
	public Location() {
		
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

	/**
	 * @return the revision
	 */
	public String getRevision() {
		return this.revision;
	}

	/**
	 * @param revision the revision to set
	 */
	public void setRevision(String revision) {
		this.revision = revision;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.loc == null) ? 0 : this.loc.hashCode());
		result = prime * result + ((this.revision == null) ? 0 : this.revision.hashCode());
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
		Location other = (Location) obj;
		if (this.loc == null) {
			if (other.loc != null)
				return false;
		} else if (!this.loc.equals(other.loc))
			return false;
		if (this.revision == null) {
			if (other.revision != null)
				return false;
		} else if (!this.revision.equals(other.revision))
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
