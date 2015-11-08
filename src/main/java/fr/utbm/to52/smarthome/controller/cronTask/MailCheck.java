/**
 * 
 */
package fr.utbm.to52.smarthome.controller.cronTask;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

import fr.utbm.to52.smarthome.services.mail.oauth.GoogleAuth;

/**
 * @author Alexandre Guyon
 *
 */
public class MailCheck implements Runnable {

	private GoogleAuth auth;
	
	private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v2/userinfo?alt=json";
	private static final String RESOURCE_GET_URL = "https://www.googleapis.com/gmail/v1/users/userId/drafts/id?alt=json";
	
	/**
	 * @param o Google Auth
	 */
	public MailCheck(GoogleAuth o) {
		if(!o.isConnected())
			o.connect();
		this.auth = o;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("Running mail check");
		System.out.println("Now we're going to access a protected resource...");
		OAuthRequest request = new OAuthRequest(Verb.GET,
				PROTECTED_RESOURCE_URL);
		this.auth.getService().signRequest(this.auth.getAccessToken(), request);
		Response response = request.send();
		System.out.println("Got it! Lets see what we found...");
		System.out.println();
		System.out.println(response.getCode());
		System.out.println(response.getBody());
		
		request = new OAuthRequest(Verb.GET,
				RESOURCE_GET_URL);
		this.auth.getService().signRequest(this.auth.getAccessToken(), request);
		response = request.send();
		System.out.println("Got it! Lets see what we found...");
		System.out.println();
		System.out.println(response.getCode());
		System.out.println(response.getBody());
	}

}
