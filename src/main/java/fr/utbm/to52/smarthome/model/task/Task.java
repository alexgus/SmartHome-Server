/**
 * 
 */
package fr.utbm.to52.smarthome.model.task;

import fr.utbm.to52.smarthome.model.calendar.Event;
import fr.utbm.to52.smarthome.model.location.Location;
import fr.utbm.to52.smarthome.model.note.Note;

/**
 * @author Alexandre Guyon
 *
 */
public class Task extends Event {

	private Note note;
	
	private Location loc;
	
	/**
	 * 
	 */
	public Task() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the note
	 */
	public Note getNote() {
		return this.note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(Note note) {
		this.note = note;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.loc == null) ? 0 : this.loc.hashCode());
		result = prime * result + ((this.note == null) ? 0 : this.note.hashCode());
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
		Task other = (Task) obj;
		if (this.loc == null) {
			if (other.loc != null)
				return false;
		} else if (!this.loc.equals(other.loc))
			return false;
		if (this.note == null) {
			if (other.note != null)
				return false;
		} else if (!this.note.equals(other.note))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Task [note=" + this.note + ", loc=" + this.loc + "]";
	}

}
