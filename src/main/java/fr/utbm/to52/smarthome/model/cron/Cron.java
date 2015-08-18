/**
 * 
 */
package fr.utbm.to52.smarthome.model.cron;

import fr.utbm.to52.smarthome.controller.Controller;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;

/**
 * Represent the current crontab install on this system
 * @author Alexandre Guyon
 *
 */
public abstract class Cron {

	/**
	 * Command for reading and writing crontab
	 */
	protected String command;
	
	/**
	 * Tag for smart app
	 */
	protected String tag;
	
	/**
	 * Tag for smart app (Ical schedule)
	 */
	protected String tagICal;
	
	/**
	 * Path to the TMP file
	 */
	protected String pathTmpFile;
	
	/**
	 * Crontab model
	 */
	protected MyTaskTable crontab;
	
	/**
	 * Crontab of this user
	 */
	protected String user;
	
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
		this.tag = Controller.getInstance().getConfig().getCronTag();
		this.tagICal = Controller.getInstance().getConfig().getCronICalTag();
		this.command = Controller.getInstance().getConfig().getCronCommand();
		this.pathTmpFile = Controller.getInstance().getConfig().getCronTMPFile();
		this.user = user;
		this.crontab = new MyTaskTable();
	}
	
	/**
	 * Apply changes
	 */
	protected abstract void apply();

	/**
	 * Get The raw crontab
	 * @return The raw crontab
	 */
	public MyTaskTable getCrontab() {
		return this.crontab;
	}
	
	/**
	 * Set existing crontab
	 * @param crontab The crontab to replace
	 */
	public void setCrontab(MyTaskTable crontab) {
		this.crontab = crontab;
		this.apply();
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
		// Test if is not already in
		int i = 0;
		while(i < this.crontab.size() 
				&& !this.crontab.getSchedulingPattern(i).toString().contains(arg0.toString()))
			++i;
		if(i >= this.crontab.size())
			this.crontab.add(arg0, arg1);
		this.apply();
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
		this.apply();
	}

	/**
	 * @return @see it.sauronsoftware.cron4j.TaskTable#size()
	 * @see it.sauronsoftware.cron4j.TaskTable#size()
	 */
	public int size() {
		return this.crontab.size();
	}
	
	/**
	 * toString for precise Line of the crontab
	 * @param i Line toString
	 * @return String to the line
	 */
	public String lineToString(int i){
		return (this.getSchedulingPattern(i) + " " + this.getTask(i));
	}
	
}
