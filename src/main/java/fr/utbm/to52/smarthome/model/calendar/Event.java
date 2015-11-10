/**
 * 
 */
package fr.utbm.to52.smarthome.model.calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import fr.utbm.to52.smarthome.model.location.Location;

/**
 * @author Alexandre Guyon
 *
 */
@Entity
@Table(name="Event")
public class Event {

	@Id @GeneratedValue(generator = "uuid")
	@GenericGenerator(name="uuid", strategy="uuid2")
	private String id;
	
    @Version
    @GeneratedValue
    @Column(name="_rev")
    private String revision;
	
    @Type(type = "java.util.Calendar")
	private DateCalendar begin;
	
    @Type(type = "java.util.Calendar")
	private DateCalendar end;
	
	private String title;
	
	@OneToOne
	@Type(type = "java.lang.String")
	private Location loc;
	
	/**
	 * 
	 */
	public Event() {
		// TODO Auto-generated constructor stub
	}

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.begin == null) ? 0 : this.begin.hashCode());
		result = prime * result + ((this.end == null) ? 0 : this.end.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.loc == null) ? 0 : this.loc.hashCode());
		result = prime * result + ((this.revision == null) ? 0 : this.revision.hashCode());
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
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
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
		return "Event [id=" + this.id + ", revision=" + this.revision + ", begin=" + this.begin + ", end=" + this.end + ", title=" + this.title
				+ ", loc=" + this.loc + "]";
	}

}
