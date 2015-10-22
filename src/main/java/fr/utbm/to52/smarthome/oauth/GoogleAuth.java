/**
 * 
 */
package fr.utbm.to52.smarthome.oauth;

import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

@SuppressWarnings("javadoc")
public class GoogleAuth {

	private static final String SCOPE = "email";

	private static final Token EMPTY_TOKEN = null;

	private String apiKey = "592316576281-16bv97ssul5rcv6q39iun9mbt0v0er3t.apps.googleusercontent.com";
	private String apiSecret = "zs1XpsstJpqz-C5gig9ZOuwQ";
	private String callbackUrl = "http://localhost:80/";


	// Create OAuthService for Google OAuth 2.0
	private OAuthService service = new ServiceBuilder().provider(Google2Api.class)
			.apiKey(this.apiKey).apiSecret(this.apiSecret).callback(this.callbackUrl)
			.scope(SCOPE).build();

	private Scanner in = new Scanner(System.in);


	private Verifier verifier = null;

	private Token accessToken = null;

	/**
	 * @return the scope
	 */
	public static String getScope() {
		return SCOPE;
	}

	/**
	 * @return the emptyToken
	 */
	public static Token getEmptyToken() {
		return EMPTY_TOKEN;
	}

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return this.apiKey;
	}

	/**
	 * @return the apiSecret
	 */
	public String getApiSecret() {
		return this.apiSecret;
	}

	/**
	 * @return the callbackUrl
	 */
	public String getCallbackUrl() {
		return this.callbackUrl;
	}

	/**
	 * @return the service
	 */
	public OAuthService getService() {
		return this.service;
	}

	/**
	 * @return the in
	 */
	public Scanner getIn() {
		return this.in;
	}

	/**
	 * @return the verifier
	 */
	public Verifier getVerifier() {
		return this.verifier;
	}

	/**
	 * @return the accessToken
	 */
	public Token getAccessToken() {
		return this.accessToken;
	}

	public static void main(String[] args) {

		GoogleAuth g = new GoogleAuth();
		g.connect();
	}

	public boolean connect(){
		try{
			String authorizationUrl = this.service.getAuthorizationUrl(EMPTY_TOKEN);
			
			System.out.println(authorizationUrl);

			System.out.println("Copy and paste the authorization code here");
			System.out.print(">>");
			this.verifier = new Verifier(this.in.nextLine());

			// Trade the Request Token and Verfier for the Access Token
			this.accessToken = this.service.getAccessToken(EMPTY_TOKEN, this.verifier);

			// Now let's go and ask for a protected resource!
			/*System.out.println("Now we're going to access a protected resource...");
			OAuthRequest request = new OAuthRequest(Verb.GET,
					PROTECTED_RESOURCE_URL);
			service.signRequest(accessToken, request);
			Response response = request.send();
			System.out.println("Got it! Lets see what we found...");
			System.out.println();
			System.out.println(response.getCode());
			System.out.println(response.getBody());*/
			
			this.in.close();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
