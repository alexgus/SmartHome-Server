/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import java.util.Date;

// TODO add client id / source of the event, message

/**
 * @author Alexandre Guyon
 *
 */
public class StorableEvent {

	private String _id;
	
    private String _rev;
    
    // TODO add table name
    
    /**
     * Date of the creation of the event
     */
    protected Date date;
    
    /**
     * Event name
     */
    protected String eventName;
    
    /**
     * Payload of the event
     */
    protected String payload; 

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

	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return this.eventName;
	}

	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the payload
	 */
	public String getPayload() {
		return this.payload;
	}

	/**
	 * @param payload the payload to set
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
		result = prime * result + ((this.eventName == null) ? 0 : this.eventName.hashCode());
		result = prime * result + ((this._id == null) ? 0 : this._id.hashCode());
		result = prime * result + ((this.payload == null) ? 0 : this.payload.hashCode());
		result = prime * result + ((this._rev == null) ? 0 : this._rev.hashCode());
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
		StorableEvent other = (StorableEvent) obj;
		if (this.date == null) {
			if (other.date != null)
				return false;
		} else if (!this.date.equals(other.date))
			return false;
		if (this.eventName == null) {
			if (other.eventName != null)
				return false;
		} else if (!this.eventName.equals(other.eventName))
			return false;
		if (this._id == null) {
			if (other._id != null)
				return false;
		} else if (!this._id.equals(other._id))
			return false;
		if (this.payload == null) {
			if (other.payload != null)
				return false;
		} else if (!this.payload.equals(other.payload))
			return false;
		if (this._rev == null) {
			if (other._rev != null)
				return false;
		} else if (!this._rev.equals(other._rev))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StorableEvent [id=" + this._id + ", revision=" + this._rev + ", date=" + this.date + ", eventName=" + this.eventName
				+ ", payload=" + this.payload + "]";
	}

}
