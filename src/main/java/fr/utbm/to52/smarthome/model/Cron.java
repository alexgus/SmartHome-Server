/**
 * 
 */
package fr.utbm.to52.smarthome.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import it.sauronsoftware.cron4j.CronParser;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskTable;

/**
 * Represent the current crontab install on this system
 * @author Alexandre Guyon
 *
 */
public class Cron {

	private static final String DEFAULT_CMD = "crontab ";
	
	private static final String DEFAULT_TAG = "#smart";
	
	private static final String DEFAULT_TMP_FILE = "/tmp/smart_crontab";
	
	private String tag;
	
	private MyTaskTable crontab;
	
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
		this.crontab = new MyTaskTable();
		this.fillCrontab();
	}

	private void fillCrontab() {
		try {
			String CMD = DEFAULT_CMD + "-l";
			Process cronp = Runtime.getRuntime().exec(CMD);
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new InputStreamReader(cronp.getInputStream()));
			
			String line = reader.readLine();
			
			while(line != null){
				if(line.contains(this.tag))
					CronParser.parseLine(this.crontab, line);
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
	public void apply(){
		File f = new File(DEFAULT_TMP_FILE);
		
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		write(f.getAbsolutePath(), this.crontab.toString());
		load();
		
		f.delete();
	}
	
	/**
	 * Write content to a file
	 * @param path The path to the file
	 * @param content The content to write
	 */
	@SuppressWarnings("resource")
	public static void write(String path, String content){ 
		try {
			
			FileWriter fw = new FileWriter(path);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void load(){
		try {
			Process cronp = Runtime.getRuntime().exec(DEFAULT_CMD + " " + DEFAULT_TMP_FILE);
			cronp.waitFor();
		} catch (IOException | InterruptedException e) {
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
	public void setCrontab(MyTaskTable crontab) {
		this.crontab = crontab;
	}

	/**
	 * Get the contab's user
	 * @return the user of the crontab loaded
	 */
	public String getUser() {
		return this.user;
	}

	/**
	 * @param arg0 @see it.sauronsoftware.cron4j.TaskTable#add(it.sauronsoftware.cron4j.SchedulingPattern, it.sauronsoftware.cron4j.Task)
	 * @param arg1 @see it.sauronsoftware.cron4j.TaskTable#add(it.sauronsoftware.cron4j.SchedulingPattern, it.sauronsoftware.cron4j.Task)
	 * @see it.sauronsoftware.cron4j.TaskTable#add(it.sauronsoftware.cron4j.SchedulingPattern, it.sauronsoftware.cron4j.Task)
	 */
	public void add(SchedulingPattern arg0, Task arg1) {
		this.crontab.add(arg0, arg1);
	}

	/**
	 * @param arg0 @see it.sauronsoftware.cron4j.TaskTable#getSchedulingPattern(int)
	 * @return @see it.sauronsoftware.cron4j.TaskTable#getSchedulingPattern(int)
	 * @throws IndexOutOfBoundsException @see it.sauronsoftware.cron4j.TaskTable#getSchedulingPattern(int)
	 * @see it.sauronsoftware.cron4j.TaskTable#getSchedulingPattern(int)
	 */
	public SchedulingPattern getSchedulingPattern(int arg0) throws IndexOutOfBoundsException {
		return this.crontab.getSchedulingPattern(arg0);
	}

	/**
	 * @param arg0 @see it.sauronsoftware.cron4j.TaskTable#getTask(int)
	 * @return @see it.sauronsoftware.cron4j.TaskTable#getTask(int)
	 * @throws IndexOutOfBoundsException @see it.sauronsoftware.cron4j.TaskTable#getTask(int)
	 * @see it.sauronsoftware.cron4j.TaskTable#getTask(int)
	 */
	public Task getTask(int arg0) throws IndexOutOfBoundsException {
		return this.crontab.getTask(arg0);
	}

	/**
	 * @param arg0 @see it.sauronsoftware.cron4j.TaskTable#remove(int)
	 * @throws IndexOutOfBoundsException @see it.sauronsoftware.cron4j.TaskTable#remove(int)
	 * @see it.sauronsoftware.cron4j.TaskTable#remove(int)
	 */
	public void remove(int arg0) throws IndexOutOfBoundsException {
		this.crontab.remove(arg0);
	}

	/**
	 * @return @see it.sauronsoftware.cron4j.TaskTable#size()
	 * @see it.sauronsoftware.cron4j.TaskTable#size()
	 */
	public int size() {
		return this.crontab.size();
	}
	
}
