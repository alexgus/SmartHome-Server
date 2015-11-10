/**
 * 
 */
package fr.utbm.to52.smarthome.controller.events;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

// TODO add client id / source of the event, message

/**
 * @author Alexandre Guyon
 *
 */
@Entity
@Table(name="Event")
public class StorableEvent {

	@Id @GeneratedValue(generator = "uuid")
	@GenericGenerator(name="uuid", strategy="uuid2")
	private String id;
	
    @Version
    @GeneratedValue
    @Column(name="_rev")
    private String revision;
    
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
	public String getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.payload == null) ? 0 : this.payload.hashCode());
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
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		if (this.payload == null) {
			if (other.payload != null)
				return false;
		} else if (!this.payload.equals(other.payload))
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
		return "StorableEvent [id=" + this.id + ", revision=" + this.revision + ", date=" + this.date + ", eventName=" + this.eventName
				+ ", payload=" + this.payload + "]";
	}

}