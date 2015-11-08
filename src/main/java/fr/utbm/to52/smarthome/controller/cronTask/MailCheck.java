/**
 * 
 */
package fr.utbm.to52.smarthome.controller.cronTask;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

import com.google.api.services.gmail.model.Message;

import fr.utbm.to52.smarthome.services.mail.GmailHelper;

/**
 * @author Alexandre Guyon
 *
 */
public class MailCheck extends TimerTask {

	private GmailHelper mail;
	
	/**
	 * @param g Google mail helper
	 */
	public MailCheck(GmailHelper g) {
		this.mail = g;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			List<Message> lm = this.mail.getMailQuery("is:unread");
			
			for (Message message : lm) {
				if(message != null){
					String id = message.getId();

					System.out.println(this.mail.getMessage(id));
					System.out.println("-----------------------");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
