/**
 * 
 */
package fr.utbm.to52.smarthome.services.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.json.simple.JSONObject;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import fr.utbm.to52.smarthome.oauth.GoogleAuth;

/**
 * @author Alexandre Guyon
 *
 */
public class GmailHelper {

	/**
	 * User email
	 */
	private static final String USER = "me";

	/**
	 * Gmail services inputed at construction
	 */
	private final Gmail gservices;

	/**
	 * Use gmail services
	 * @param g Gmail object from given google's API
	 */
	public GmailHelper(Gmail g) {
		this.gservices = g;
	}

	/**
	 * Query gmail
	 * @param query In gmail format
	 * @return Message's list
	 * @throws IOException Throw IO network exception
	 */
	public List<Message> getMailQuery(String query) throws IOException{
		return this.gservices.users().messages().list(USER).setQ(query).execute().getMessages();
	}

	/**
	 * Get message with its ID in string format
	 * @param messageId Message id
	 * @return String in JSON format
	 */
	public String getMessage(String messageId) {
		return new JSONObject(getMessageDetails(messageId)).toJSONString();
	}
	
	/**
	 * Get message with its ID in JSON format
	 * @param messageId Message id
	 * @return JSON format of the mail
	 */
	public JSONObject getJSONMessage(String messageId) {
		return new JSONObject(getMessageDetails(messageId));
	}

	/**
	 * Get message details in map
	 * @param messageId THe message to retrieve
	 * @return Map with all properties
	 */
	public Map<String, Object> getMessageDetails(String messageId) {
		Map<String, Object> messageDetails = new HashMap<>();
		try {
			Message message = this.gservices.users().messages().get(USER, messageId).setFormat("raw").execute();

			byte[] emailBytes = Base64.decodeBase64(message.getRaw());

			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			MimeMessage email = new MimeMessage(session, new ByteArrayInputStream(emailBytes));
			messageDetails.put("subject", email.getSubject());
			messageDetails.put("from", email.getSender() != null ? email.getSender().toString() : "None");
			messageDetails.put("time", email.getSentDate() != null ? email.getSentDate().toString() : "None");
			messageDetails.put("snippet", message.getSnippet());
			messageDetails.put("threadId", message.getThreadId());
			messageDetails.put("id", message.getId());
			messageDetails.put("body", getText(email));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return messageDetails;

	}

	/**
	 * Return the primary text content of the message.
	 */
	private String getText(Part p) throws
	MessagingException, IOException {
		if (p.isMimeType("text/*")) {
			String s = (String) p.getContent();
			return s;
		}

		if (p.isMimeType("multipart/alternative")) {
			// prefer html text over plain text
			Multipart mp = (Multipart) p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null) {
						text = getText(bp);
					}
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null) {
						return s;
					}
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null) {
					return s;
				}
			}
		}

		return null;
	}


	@SuppressWarnings("javadoc")
	public static void main(String[] args){
		GoogleAuth g = new GoogleAuth(GoogleAuth.apiKeyDEMO,GoogleAuth.apiSecretDEMO,GmailScopes.GMAIL_READONLY);
		g.connect();

		try {
			GmailHelper mail = new GmailHelper(g.getGmailService());

			List<Message> lm = mail.getMailQuery("after:2015/10/24 before:2015/10/27");

			for (Message message : lm) {
				if(message != null){
					String id = message.getId();

					System.out.println(mail.getMessage(id));
					System.out.println("-----------------------");
				}
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
