/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.ogm.OgmSession;
import org.json.JSONObject;

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

	@Override
	public List<Note> getData() {
		List<Note> l = new ArrayList<>();
		
		this.hbmSess.beginTransaction();
		
		Query sql = ((OgmSession) this.hbmSess).createNativeQuery("function(doc) {emit(null, doc);}");
		l = sql.list();
		
		this.hbmSess.getTransaction().commit();
		
		return l;
	}

	@Override
	public List<Note> getData(JSONObject criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
