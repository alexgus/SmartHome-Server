/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import java.util.Date;

import fr.utbm.to52.smarthome.services.couchdb.StorableEntity;

// TODO add client id / source of the event, message --> Can't from mqtt

/**
 * @author Alexandre Guyon
 *
 */
public class StorableEvent extends StorableEntity{

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
	 * Default constructor
	 */
    public StorableEvent() {
		super(StorableEvent.class.getName());
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

	/**
	 * @return the $table
	 */
	public String get$table() {
		return this.$table;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.$table == null) ? 0 : this.$table.hashCode());
		result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
		result = prime * result + ((this.eventName == null) ? 0 : this.eventName.hashCode());
		result = prime * result + ((this.payload == null) ? 0 : this.payload.hashCode());
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
		if (this.$table == null) {
			if (this.$table != null)
				return false;
		} else if (!this.$table.equals(this.$table))
			return false;
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
		if (this.payload == null) {
			if (other.payload != null)
				return false;
		} else if (!this.payload.equals(other.payload))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "this [$table=" + this.$table + ", date=" + this.date + ", eventName=" + this.eventName + ", payload="
				+ this.payload + "]";
	}

}
