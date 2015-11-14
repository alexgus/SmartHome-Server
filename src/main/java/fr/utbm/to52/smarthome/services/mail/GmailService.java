/**
 * 
 */
package fr.utbm.to52.smarthome.services.mail;

import java.io.IOException;
import java.util.Timer;

import com.google.api.services.gmail.GmailScopes;

import fr.utbm.to52.smarthome.controller.Conf;
import fr.utbm.to52.smarthome.controller.periodicTask.MailCheck;
import fr.utbm.to52.smarthome.services.AbstractService;
import fr.utbm.to52.smarthome.services.mail.oauth.GoogleAuth;

/**
 * @author Alexandre Guyon
 *
 */
public class GmailService extends AbstractService{

	private static final long CHECK_TIME = 1000 * 60 * 5; // 5 minutes in milliseconds
	
	private GoogleAuth auth;
	
	private GmailHelper mail;
	
	private Timer sched;
	
	private MailCheck mailCheck;
	
	/**
	 * Log to google server 
	 */
	public GmailService() {	
		this.sched = new Timer(true);
	}
	
	public void setUp(Conf c) {
		super.setUp(c);

		this.auth = new GoogleAuth(this.config.getGoogleApiKey(), 
				this.config.getGoogleApiSecret(), 
				GmailScopes.GMAIL_READONLY);

		this.auth.connect();
		
		try {
			this.mail = new GmailHelper(this.auth.getGmailService());
			this.mailCheck = new MailCheck(this.mail);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		this.sched.scheduleAtFixedRate(this.mailCheck, 0, CHECK_TIME);
	}

	@Override
	public void stop() {
		// TODO supress auth
	}

}
