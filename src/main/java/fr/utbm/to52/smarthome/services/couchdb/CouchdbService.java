/**
 * 
 */
package fr.utbm.to52.smarthome.services.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.model.note.Note;
import fr.utbm.to52.smarthome.repository.DAO;
import fr.utbm.to52.smarthome.repository.NoteDAO;
import fr.utbm.to52.smarthome.services.AbstractService;

/**
 * @author Alexandre Guyon
 *
 */
public class CouchdbService extends AbstractService {

	private CouchDbClient session;

	private List<DAO<?>> lDAO;
	
	private NoteDAO note = null;
	
	/**
	 * Create a couchdb service
	 */
	public CouchdbService() {
		this.lDAO = new ArrayList<>();
		
		this.note = new NoteDAO();
		this.lDAO.add(this.note);
	}
	
	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.services.AbstractService#start()
	 */
	@Override
	public void start() {
		this.session = new CouchDbClient();
		
		for (DAO<?> dao : this.lDAO) 
			dao.setUp(this.session);
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.services.AbstractService#stop()
	 */
	@Override
	public void stop() {
		this.session.shutdown();
	}

	/**
	 * @return the client
	 */
	public CouchDbClient getSession() {
		return this.session;
	}

	/**
	 * @return return new note DAO
	 */
	public DAO<Note> getNoteDao() {
		return this.note;
	}

}
