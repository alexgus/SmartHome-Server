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
@SuppressWarnings("boxing")
public class NoteDAO extends AbstractDAO<Note> {

	public void setUp(org.lightcouch.CouchDbClient s) {
		super.setUp(s);
	}


	@Override
	public void save(Note data) {
		this.couch.save(data);
	}

	@Override
	public List<Note> getData() {
		return this.couch.view(this.designDoc+ "/list")
				.includeDocs(true)
				.query(Note.class);
	}

	@Override
	public String getRawData() {
		return AbstractDAO.getStringFromView(this.couch.view(this.designDoc+ "/list")
				.includeDocs(true));
	}

	@Override
	public List<Note> getData(JSONObject criteria) { // TODO Improve code
		String tag = "";

		if(criteria.has("tag")){
			tag = criteria.getString("tag");

			return this.couch.view(this.designDoc+ "/listByTag")
					.includeDocs(true)
					.descending(true)
					.endKey(tag)
					.query(Note.class);
		}
		return null;
	}

	@SuppressWarnings({ "unused" })
	@Override
	public String getRawData(JSONObject criteria) {
		String tag = "";
		String content = "";

		if(criteria.has("tag")){
			tag = criteria.getString("tag");

			return AbstractDAO.getStringFromView(this.couch.view(this.designDoc+ "/listByTag")
					.includeDocs(true)
					.descending(true)
					.endKey(tag));
		}
		return null;
	}

}
