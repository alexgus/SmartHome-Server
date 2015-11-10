/**
 * 
 */
package fr.utbm.to52.smarthome.model.note;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Alexandre Guyon
 *
 */
@Entity
@Table(name="Note")
public class Note {
	
	@Id @GeneratedValue(generator = "uuid")
	@GenericGenerator(name="uuid", strategy="uuid2")
	private String id;

    @Version
    @GeneratedValue
    @Column(name="_rev")
    private String revision;
	
	private Date date;

	private String tag;

	private String note;

	/**
	 * Default constructor
	 */
	public Note(){
		this.date = new Date();
	}
	
	/**
	 * @param note The note to create
	 */
	public Note(String note) {
		this.note = note;
		this.date = new Date();
	}

	/**
	 * @param d The date to create the note
	 * @param s The string in the note
	 */
	public Note(Date d, String s){
		this.date = d;
		this.note = s;
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
	 * @return the tag
	 */
	public String getTag() {
		return this.tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return this.note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
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
		result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.note == null) ? 0 : this.note.hashCode());
		result = prime * result + ((this.revision == null) ? 0 : this.revision.hashCode());
		result = prime * result + ((this.tag == null) ? 0 : this.tag.hashCode());
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
		Note other = (Note) obj;
		if (this.date == null) {
			if (other.date != null)
				return false;
		} else if (!this.date.equals(other.date))
			return false;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		if (this.note == null) {
			if (other.note != null)
				return false;
		} else if (!this.note.equals(other.note))
			return false;
		if (this.revision == null) {
			if (other.revision != null)
				return false;
		} else if (!this.revision.equals(other.revision))
			return false;
		if (this.tag == null) {
			if (other.tag != null)
				return false;
		} else if (!this.tag.equals(other.tag))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Note [id=" + this.id + ", revision=" + this.revision + ", date=" + this.date + ", tag=" + this.tag + ", note=" + this.note + "]";
	}

}
