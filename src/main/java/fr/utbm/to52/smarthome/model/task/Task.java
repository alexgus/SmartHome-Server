/**
 * 
 */
package fr.utbm.to52.smarthome.model.task;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import fr.utbm.to52.smarthome.model.calendar.Event;
import fr.utbm.to52.smarthome.model.note.Note;

/**
 * @author Alexandre Guyon
 *
 */
@Entity
@Table(name="Task")
public class Task extends Event {
	
	@OneToOne
	private Note note;
	
	
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


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		return "Task [event " + super.toString() + ", note=" + this.note + "]";
	}

}
