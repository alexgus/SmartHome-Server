/**
 * 
 */
package fr.utbm.to52.smarthome.controller.conf;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Alexandre Guyon
 *
 */
public class ClockFeature {

	private String cronSource;
	
	private String cronCommand;
	
	private String cronTag;
	
	private String cronICalTag;
	
	private String cronTmpFile;
	
	private int internalServerPort;
	
	private String wakeUpTimeBeforeEvent;
	
	private List<String> calendars;

	/**
	 * Initialize object with json
	 * @param json json data
	 */
	public ClockFeature(JSONObject json) {
		JSONObject cron = json.getJSONObject("cron");
		this.cronSource = cron.getString("source");
		this.cronCommand = cron.getString("command");
		this.cronTag = cron.getString("tag");
		this.cronICalTag = cron.getString("icalTag");
		this.cronTmpFile = cron.getString("tmpFile");
		
		this.internalServerPort = json.getInt("internalServerPort");
		this.wakeUpTimeBeforeEvent = json.getString("wakeUpTimeBeforeEvent");
		this.calendars = new ArrayList<>();
		JSONArray cal = json.getJSONArray("calendars");
		for(int i = 0 ; i < cal.length() ; ++i)
			this.calendars.add(cal.getString(i));
	}	
	
	/**
	 * @return the cronSource
	 */
	public String getCronSource() {
		return this.cronSource;
	}

	/**
	 * @param cronSource the cronSource to set
	 */
	public void setCronSource(String cronSource) {
		this.cronSource = cronSource;
	}

	/**
	 * @return the cronCommand
	 */
	public String getCronCommand() {
		return this.cronCommand;
	}

	/**
	 * @param cronCommand the cronCommand to set
	 */
	public void setCronCommand(String cronCommand) {
		this.cronCommand = cronCommand;
	}

	/**
	 * @return the cronTag
	 */
	public String getCronTag() {
		return this.cronTag;
	}

	/**
	 * @param cronTag the cronTag to set
	 */
	public void setCronTag(String cronTag) {
		this.cronTag = cronTag;
	}

	/**
	 * @return the cronICalTag
	 */
	public String getCronICalTag() {
		return this.cronICalTag;
	}

	/**
	 * @param cronICalTag the cronICalTag to set
	 */
	public void setCronICalTag(String cronICalTag) {
		this.cronICalTag = cronICalTag;
	}

	/**
	 * @return the cronTmpFile
	 */
	public String getCronTmpFile() {
		return this.cronTmpFile;
	}

	/**
	 * @param cronTmpFile the cronTmpFile to set
	 */
	public void setCronTmpFile(String cronTmpFile) {
		this.cronTmpFile = cronTmpFile;
	}

	/**
	 * @return the internalServerPort
	 */
	public int getInternalServerPort() {
		return this.internalServerPort;
	}

	/**
	 * @param internalServerPort the internalServerPort to set
	 */
	public void setInternalServerPort(int internalServerPort) {
		this.internalServerPort = internalServerPort;
	}

	/**
	 * @return the wakeUpTimeBeforeEvent
	 */
	public String getWakeUpTimeBeforeEvent() {
		return this.wakeUpTimeBeforeEvent;
	}

	/**
	 * @param wakeUpTimeBeforeEvent the wakeUpTimeBeforeEvent to set
	 */
	public void setWakeUpTimeBeforeEvent(String wakeUpTimeBeforeEvent) {
		this.wakeUpTimeBeforeEvent = wakeUpTimeBeforeEvent;
	}

	/**
	 * @return the calendars
	 */
	public List<String> getCalendars() {
		return this.calendars;
	}

	/**
	 * @param calendars the calendars to set
	 */
	public void setCalendars(List<String> calendars) {
		this.calendars = calendars;
	}
	
}
