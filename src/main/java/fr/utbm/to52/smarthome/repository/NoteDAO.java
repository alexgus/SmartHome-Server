/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import java.util.List;

import org.json.JSONObject;

import fr.utbm.to52.smarthome.model.note.Note;

/**
 * @author Alexandre Guyon
 *
 */
public class NoteDAO extends AbstractDAO<Note> {

	@Override
	public void save(Note data) {
		this.couch.save(data);
	}

	@Override
	public List<Note> getData() {
		// TODO implement view		
		return null;
	}

	@Override
	public List<Note> getData(JSONObject criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
