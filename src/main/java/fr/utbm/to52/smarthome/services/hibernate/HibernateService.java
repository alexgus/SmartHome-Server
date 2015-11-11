/**
 * 
 */
package fr.utbm.to52.smarthome.services.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ogm.OgmSession;

import fr.utbm.to52.smarthome.repository.DAO;
import fr.utbm.to52.smarthome.repository.NoteDAO;
import fr.utbm.to52.smarthome.services.AbstractService;
import fr.utbm.to52.smarthome.util.HibernateUtil;

/**
 * Get hibernate service
 * @author Alexandre Guyon
 *
 */
public class HibernateService extends AbstractService {

	private OgmSession hbm;
	
	private List<DAO<?>> lDAO = new ArrayList<>();
	
	// DAO
	
	private NoteDAO noteDao;

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.services.AbstractService#start()
	 */
	@Override
	public void start() {
		this.hbm = HibernateUtil.getSessionFactory().openSession();
		
		this.noteDao = new NoteDAO();
		this.lDAO.add(this.noteDao);
		
		for (DAO<?> dao : this.lDAO)
			dao.setUp(this.hbm);
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.services.AbstractService#stop()
	 */
	@Override
	public void stop() {
		this.hbm.flush();
		this.hbm.close();
	}

	/**
	 * @return the hbm
	 */
	public OgmSession getHbm() {
		return this.hbm;
	}

	/**
	 * @param hbm the hbm to set
	 */
	public void setHbm(OgmSession hbm) {
		this.hbm = hbm;
	}

	/**
	 * @return the noteDao
	 */
	public NoteDAO getNoteDao() {
		return this.noteDao;
	}

	/**
	 * @param noteDao the noteDao to set
	 */
	public void setNoteDao(NoteDAO noteDao) {
		this.noteDao = noteDao;
	}

}
