/**
 * 
 */
package fr.utbm.to52.smarthome.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import it.sauronsoftware.cron4j.CronParser;
import it.sauronsoftware.cron4j.TaskTable;

/**
 * Represent the current crontab install on this system
 * @author Alexandre Guyon
 *
 */
public class Cron {

	private static final String DEFAULT_CMD = "crontab -l -u ";
	
	private static final String DEFAULT_TAG = "#smart";
	
	private String tag;
	
	private TaskTable crontab;
	
	private String user;
	
	/**
	 * Default constructor. Use the user's crontab which launch the java process
	 */
	public Cron(){
		this(System.getProperty("user.name"));
	}
	
	/**
	 * To search a specific crontab
	 * @param user The user you want the crontab to be loaded
	 */
	public Cron(String user){
		this.tag = DEFAULT_TAG;
		this.user = user;
		this.crontab = new TaskTable();
		this.fillCrontab();
	}

	private void fillCrontab() {
		try {
			Process cronp = Runtime.getRuntime().exec(DEFAULT_CMD + this.user);
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new InputStreamReader(cronp.getInputStream()));
			
			String line = reader.readLine();
			
			while(line != null){
				if(line.contains(this.tag))
					CronParser.parseLine(this.crontab, line);
				line = reader.readLine();
			}
			
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get The raw crontab
	 * @return The raw crontab
	 */
	public TaskTable getCrontab() {
		return this.crontab;
	}
	
	/**
	 * Set existing crontab
	 * @param crontab The crontab to replace
	 */
	public void setCrontab(TaskTable crontab) {
		this.crontab = crontab;
	}

	/**
	 * Get the contab's user
	 * @return the user of the crontab loaded
	 */
	public String getUser() {
		return this.user;
	}
	
}
