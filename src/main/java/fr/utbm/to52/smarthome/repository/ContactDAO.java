/**
 * 
 */
package fr.utbm.to52.smarthome.repository;

import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.model.contact.Contact;

/**
 * @author Alexandre Guyon
 *
 */
public class ContactDAO extends AbstractDAO<Contact> {

	@Override
	public void setUp(CouchDbClient s) {
		super.setUp(s, Contact.class);
		
		this.available_actions.add(AbstractDAO.ACTION_SAVE);
		this.available_actions.add(AbstractDAO.ACTION_LIST);
	}

}
