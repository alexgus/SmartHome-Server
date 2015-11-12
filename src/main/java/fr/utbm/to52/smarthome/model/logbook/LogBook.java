/**
 * 
 */
package fr.utbm.to52.smarthome.model.logbook;

import java.util.List;

import fr.utbm.to52.smarthome.model.note.Note;

/**
 * @author Alexandre Guyon
 *
 */
public class LogBook {

	List<Note> lNote;

	/**
	 * @return the lNote
	 */
	public List<Note> getlNote() {
		return this.lNote;
	}

	/**
	 * @param lNote the lNote to set
	 */
	public void setlNote(List<Note> lNote) {
		this.lNote = lNote;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.lNote == null) ? 0 : this.lNote.hashCode());
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
		LogBook other = (LogBook) obj;
		if (this.lNote == null) {
			if (other.lNote != null)
				return false;
		} else if (!this.lNote.equals(other.lNote))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LogBook [lNote=" + this.lNote + "]";
	}

}
