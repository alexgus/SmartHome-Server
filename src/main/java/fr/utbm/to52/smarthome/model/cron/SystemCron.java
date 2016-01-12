/**
 * 
 */
package fr.utbm.to52.smarthome.model.cron;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import fr.utbm.to52.smarthome.util.BasicIO;
import it.sauronsoftware.cron4j.CronParser;

/**
 * @author Alexandre Guyon
 *
 */
public class SystemCron extends RingCron{

	/**
	 * Default constructor
	 */
	public SystemCron() {
		super();
		this.fillCrontab();
	}

	/**
	 * Set the user's crontab to handle
	 * @param user User's crontab
	 */
	public SystemCron(String user) {
		super(user);
		this.fillCrontab();
	}
	
	/**
	 * Fill the model with the system's crontab
	 */
	protected void fillCrontab() {
		this.fillCrontab(this.crontab);
	}

	private void fillCrontab(MyTaskTable crontab2) {
		this.fillCrontab(crontab2, 1);
	}

	private void fillCrontab(MyTaskTable tt, int checkTag) {
		try {
			String CMD = this.command + " -l";
			Process cronp = Runtime.getRuntime().exec(CMD);
			BufferedReader reader = new BufferedReader(new InputStreamReader(cronp.getInputStream()));
			
			String line = reader.readLine();
			
			while(line != null){
				if(checkTag >= 0 && 
						(line.contains(this.tag) || line.contains(this.tagICal)))
					CronParser.parseLine(tt, line);
				else if(checkTag <= 0 && 
						(!line.contains(this.tag) || !line.contains(this.tagICal)))
					CronParser.parseLine(tt, line);
				line = reader.readLine();
			}
			
			cronp.waitFor();
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write the current crontab and load it
	 */
	protected void apply(){
		File f = new File(this.pathTmpFile);
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		MyTaskTable tt = new MyTaskTable();
		this.fillCrontab(tt,-1); // Fill this tab with all non tagged lines
		
		BasicIO.write(f.getAbsolutePath(), tt.toString() + "\n" + this.crontab.toString());
		this.load();
		
		f.delete();
	}
	
	/**
	 * Load user's crontab
	 */
	protected void load(){
		try {
			Process cronp = Runtime.getRuntime().exec(this.command + " " + this.pathTmpFile);
			cronp.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
