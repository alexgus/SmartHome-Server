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

	/**
	 * Private API key of the app
	 */
	private static final String apiKeyDEMO = "592316576281-16bv97ssul5rcv6q39iun9mbt0v0er3t.apps.googleusercontent.com";
	
	/**
	 * Private secret of the app
	 */
	private static final String apiSecretDEMO = "zs1XpsstJpqz-C5gig9ZOuwQ";
	
	/**
	 * ???
	 */
	private static final Token EMPTY_TOKEN = null;

	private String scope;
	
	/**
	 * Private API key of the app
	 */
	private String apiKey;
	
	/**
	 * Private secret of the app
	 */
	private String apiSecret;
	
	/**
	 * CallBack URL
	 */
	private String callbackUrl = "http://localhost:80/";

	/**
	 * is this auth is currently on or not
	 */
	private boolean isConnected = false;

	private Token accessToken = null;
	
	// Create OAuthService for Google OAuth 2.0
	private OAuthService service;

	public GoogleAuth(String apiKey, String apiSecret, String scope){
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.scope = scope;
		this.service = new ServiceBuilder().provider(Google2Api.class)
				.apiKey(this.apiKey).apiSecret(this.apiSecret).callback(this.callbackUrl)
				.scope(this.scope).build();
	}
	
	@SuppressWarnings("resource")
	public boolean connect(){
		Verifier verifier = null;
		
		Scanner scanner = new Scanner(System.in);
		try{
			String authorizationUrl = this.service.getAuthorizationUrl(EMPTY_TOKEN);
			
			System.out.println(authorizationUrl);

			System.out.println("Copy and paste the authorization code here");
			System.out.print(">>");
			verifier = new Verifier(scanner.nextLine());

			// Trade the Request Token and Verfier for the Access Token
			this.accessToken = this.service.getAccessToken(EMPTY_TOKEN, verifier);

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
			
			this.isConnected = true;
		}catch(Exception e){
			e.printStackTrace();
			this.isConnected = false;
		}finally{
			scanner.close();
		}
		
		return this.isConnected;
	}
	
	/**
	 * @return the scope
	 */
	public String getScope() {
		return this.scope;
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
	 * @return the accessToken
	 */
	public Token getAccessToken() {
		return this.accessToken;
	}

	/**
	 * @return the isConnected
	 */
	public boolean isConnected() {
		return this.isConnected;
	}
	
	public static void main(String[] args) {
		GoogleAuth g = new GoogleAuth(apiKeyDEMO,apiSecretDEMO,"mail");
		g.connect();
	}
}
