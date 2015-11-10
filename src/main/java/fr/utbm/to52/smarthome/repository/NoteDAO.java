/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import fr.utbm.to52.smarthome.model.note.Note;

/**
 * @author Alexandre Guyon
 *
 */
public class NoteDAO extends AbstractDAO<Note> {

	@Override
	public void save(Note data) {
		this.hbmSess.beginTransaction();
		
		this.hbmSess.save(data);
		this.hbmSess.flush();
		
		this.hbmSess.getTransaction().commit();
	}

}
