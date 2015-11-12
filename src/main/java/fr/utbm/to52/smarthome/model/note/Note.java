/**
 * 
 */
package fr.utbm.to52.smarthome.model.note;

import java.util.Date;

/**
 * @author Alexandre Guyon
 *
 */
public class Note {
	
	private String _id;

    private String _rev;
	
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
		return this._id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this._id = id;
	}

	/**
	 * @return the revision
	 */
	public String getRevision() {
		return this._rev;
	}

	/**
	 * @param revision the revision to set
	 */
	public void setRevision(String revision) {
		this._rev = revision;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
		result = prime * result + ((this._id == null) ? 0 : this._id.hashCode());
		result = prime * result + ((this.note == null) ? 0 : this.note.hashCode());
		result = prime * result + ((this._rev == null) ? 0 : this._rev.hashCode());
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
		if (this._id == null) {
			if (other._id != null)
				return false;
		} else if (!this._id.equals(other._id))
			return false;
		if (this.note == null) {
			if (other.note != null)
				return false;
		} else if (!this.note.equals(other.note))
			return false;
		if (this._rev == null) {
			if (other._rev != null)
				return false;
		} else if (!this._rev.equals(other._rev))
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
		return "Note [id=" + this._id + ", revision=" + this._rev + ", date=" + this.date + ", tag=" + this.tag + ", note=" + this.note + "]";
	}

}
