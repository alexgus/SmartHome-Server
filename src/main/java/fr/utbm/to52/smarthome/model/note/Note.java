/**
 * 
 */
package fr.utbm.to52.smarthome.model.note;

import java.util.Date;

import fr.utbm.to52.smarthome.services.couchdb.StorableEntity;

/**
 * @author Alexandre Guyon
 *
 */
public class Note extends StorableEntity{
	
	private Date date;

	private String tag;

	private String note;

	/**
	 * Default constructor
	 */
	public Note(){
		super(Note.class.getName());
		this.date = new Date();
	}
	
	/**
	 * @param note The note to create
	 */
	public Note(String note) {
		super(Note.class.getName());
		this.note = note;
		this.date = new Date();
	}

	/**
	 * @param d The date to create the note
	 * @param s The string in the note
	 */
	public Note(Date d, String s){
		super(Note.class.getName());
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
		result = prime * result + ((this.note == null) ? 0 : this.note.hashCode());
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
		if (this.note == null) {
			if (other.note != null)
				return false;
		} else if (!this.note.equals(other.note))
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
		return "Note [date=" + this.date + ", tag=" + this.tag + ", note=" + this.note + "]";
	}

}
