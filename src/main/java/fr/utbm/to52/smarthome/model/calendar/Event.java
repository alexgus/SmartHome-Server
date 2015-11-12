/**
 * 
 */
package fr.utbm.to52.smarthome.model.calendar;

import fr.utbm.to52.smarthome.model.location.Location;

/**
 * @author Alexandre Guyon
 *
 */
public class Event {

	private String _id;
	
    private String _rev;
	
	private DateCalendar begin;
	
	private DateCalendar end;
	
	private String title;

	private Location loc;

	/**
	 * @return the begin
	 */
	public DateCalendar getBegin() {
		return this.begin;
	}

	/**
	 * @param begin the begin to set
	 */
	public void setBegin(DateCalendar begin) {
		this.begin = begin;
	}

	/**
	 * @return the end
	 */
	public DateCalendar getEnd() {
		return this.end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(DateCalendar end) {
		this.end = end;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the loc
	 */
	public Location getLoc() {
		return this.loc;
	}

	/**
	 * @param loc the loc to set
	 */
	public void setLoc(Location loc) {
		this.loc = loc;
	}

	/**
	 * @return the id
	 */
	public String get_id() {
		return this._id;
	}

	/**
	 * @param id the id to set
	 */
	public void set_id(String id) {
		this._id = id;
	}

	/**
	 * @return the revision
	 */
	public String get_rev() {
		return this._rev;
	}

	/**
	 * @param revision the revision to set
	 */
	public void set_rev(String revision) {
		this._rev = revision;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.begin == null) ? 0 : this.begin.hashCode());
		result = prime * result + ((this.end == null) ? 0 : this.end.hashCode());
		result = prime * result + ((this._id == null) ? 0 : this._id.hashCode());
		result = prime * result + ((this.loc == null) ? 0 : this.loc.hashCode());
		result = prime * result + ((this._rev == null) ? 0 : this._rev.hashCode());
		result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
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
		Event other = (Event) obj;
		if (this.begin == null) {
			if (other.begin != null)
				return false;
		} else if (!this.begin.equals(other.begin))
			return false;
		if (this.end == null) {
			if (other.end != null)
				return false;
		} else if (!this.end.equals(other.end))
			return false;
		if (this._id == null) {
			if (other._id != null)
				return false;
		} else if (!this._id.equals(other._id))
			return false;
		if (this.loc == null) {
			if (other.loc != null)
				return false;
		} else if (!this.loc.equals(other.loc))
			return false;
		if (this._rev == null) {
			if (other._rev != null)
				return false;
		} else if (!this._rev.equals(other._rev))
			return false;
		if (this.title == null) {
			if (other.title != null)
				return false;
		} else if (!this.title.equals(other.title))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Event [id=" + this._id + ", revision=" + this._rev + ", begin=" + this.begin + ", end=" + this.end + ", title=" + this.title
				+ ", loc=" + this.loc + "]";
	}

}
