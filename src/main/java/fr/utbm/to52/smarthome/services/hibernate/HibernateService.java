/**
 * 
 */
package fr.utbm.to52.smarthome.services.hibernate;

import org.hibernate.Session;

import fr.utbm.to52.smarthome.services.AbstractService;
import fr.utbm.to52.smarthome.util.HibernateUtil;

/**
 * Get hibernate service
 * @author Alexandre Guyon
 *
 */
public class HibernateService extends AbstractService {

	private Session hbm;

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.services.AbstractService#start()
	 */
	@Override
	public void start() {
		this.hbm = HibernateUtil.getSessionFactory().openSession();
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
	public Session getHbm() {
		return this.hbm;
	}

	/**
	 * @param hbm the hbm to set
	 */
	public void setHbm(Session hbm) {
		this.hbm = hbm;
	}

}
