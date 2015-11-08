/**
 * 
 */
package fr.utbm.to52.smarthome.services.mail;

import java.io.IOException;

import com.google.api.services.gmail.GmailScopes;

import fr.utbm.to52.smarthome.oauth.GoogleAuth;
import fr.utbm.to52.smarthome.services.AbstractService;

/**
 * @author Alexandre Guyon
 *
 */
public class GmailService extends AbstractService{

	private GoogleAuth auth;
	
	private GmailHelper mail;
	
	/**
	 * Log to google server 
	 */
	public GmailService() {
		this.auth = new GoogleAuth(this.config.getGoogleApiKey(), 
				this.config.getGoogleApiSecret(), 
				GmailScopes.GMAIL_READONLY);		
	}

	@Override
	public void start() {
		this.auth.connect();
		try {
			this.mail = new GmailHelper(this.auth.getGmailService());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		// TODO supress auth
	}

}
