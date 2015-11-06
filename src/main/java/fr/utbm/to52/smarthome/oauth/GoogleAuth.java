package fr.utbm.to52.smarthome.oauth;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;

/**
 * Helper class for creating valid google authentication.
 * Print in console URL user must access and accept.
 * Next return a code (in the redirected url, next granted
 * access) inputed by the user to the console.
 * 
 * @author Alexandre Guyon
 *
 */
@SuppressWarnings("javadoc")
public class GoogleAuth {

	private static final String APP_NAME = "Smart - Google Auth";

	/** 
	 * Global instance of the JSON factory. 
	 */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** 
	 * Global instance of the HTTP transport. 
	 */
	private static HttpTransport HTTP_TRANSPORT;

	/**
	 * ???
	 */
	private static final Token EMPTY_TOKEN = null;

	/**
	 * Scope to authenticate
	 */
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
	private String callbackUrl = "http://localhost:8090/"; // TODO get return code

	/**
	 * is this auth is currently on or not
	 */
	private boolean isConnected = false;

	/**
	 * Access token of the successful auth
	 */
	private Token accessToken = null;

	/**
	 * OAuthService for Google OAuth 2.0
	 */
	private OAuthService service;

	/**
	 * Google credential
	 */
	private Credential googleCred;

	/**
	 * Create authentication on google server
	 * @param apiKey Apikey of your app
	 * @param apiSecret Api secret of your app
	 * @param scope scope your app want to access
	 */
	public GoogleAuth(String apiKey, String apiSecret, String scope){
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.scope = scope;
		this.service = new ServiceBuilder().provider(Google2Api.class)
				.apiKey(this.apiKey).apiSecret(this.apiSecret).callback(this.callbackUrl)
				.scope(this.scope).build();

		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("boxing")
	private Credential createCredentialWithAccessTokenOnly(String rawToken){
		TokenResponse tok = new TokenResponse();
		JSONObject j = new JSONObject(rawToken);
		tok.setAccessToken(j.getString("access_token"));
		tok.setTokenType(j.getString("token_type"));
		tok.setExpiresInSeconds(j.getLong("expires_in"));
		tok.setRefreshToken(j.getString("refresh_token"));

		return createCredentialWithRefreshToken(
				new NetHttpTransport(), 
				JacksonFactory.getDefaultInstance(), 
				new GoogleClientSecrets().setInstalled(new GoogleClientSecrets.Details().setClientId(this.apiKey)), // FIXME Crappyyyyy !!!
				tok);
	}

	public static GoogleCredential createCredentialWithRefreshToken(HttpTransport transport,
			JsonFactory jsonFactory, GoogleClientSecrets clientSecrets, TokenResponse tokenResponse) {
		return new GoogleCredential.Builder().setTransport(transport)
				.setJsonFactory(jsonFactory)
				.setClientSecrets(clientSecrets)
				.build()
				.setFromTokenResponse(tokenResponse);
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

			this.googleCred = createCredentialWithAccessTokenOnly(this.accessToken.getRawResponse());
			System.out.println("refresh : " + this.googleCred);
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

	/**
	 * Private API key of the app
	 */
	public static final String apiKeyDEMO = "592316576281-16bv97ssul5rcv6q39iun9mbt0v0er3t.apps.googleusercontent.com";

	/**
	 * Private secret of the app
	 */
	public static final String apiSecretDEMO = "zs1XpsstJpqz-C5gig9ZOuwQ";

	/**
	 * Build and return an authorized Gmail client service.
	 * @return an authorized Gmail client service
	 * @throws IOException
	 */
	public Gmail getGmailService() throws IOException {
		Credential credential = this.googleCred;
		return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APP_NAME)
				.build();
	}

	/**
	 * Build and return an authorized Calendar client service.
	 * @return an authorized Calendar client service
	 * @throws IOException
	 */
	public Calendar getCalendarService() throws IOException {
		Credential credential = this.googleCred;
		return new Calendar.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APP_NAME)
				.build();
	}

	public static void main(String[] args) {
		String user = "me";

		GoogleAuth g = new GoogleAuth(apiKeyDEMO,apiSecretDEMO,GmailScopes.GMAIL_READONLY);
		g.connect();

		List<com.google.api.services.gmail.model.Thread> l;
		try {
			l = g.getGmailService().users().threads().list(user).setQ("after:2015/10/24 before:2015/10/27").execute().getThreads();

			for (com.google.api.services.gmail.model.Thread thread : l)
				System.out.println(thread.toPrettyString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
