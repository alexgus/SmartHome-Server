package fr.utbm.to52.smarthome.controller;

import org.json.JSONObject;

import fr.utbm.to52.smarthome.util.BasicIO;

/**
 * This class is used to parse the configuration file.
 * This is also used to store all this values.
 * @author Alexandre Guyon
 *
 */
public class Conf {

	private static final String DEFAULT_CONF_FILE = "smart.conf";
	
	private String MQTTServer;
	
	private String MQTTID;

	private String MQTTRingTopic;

	private String MQTTRingPayload;

	private int MQTTQOS;
	
	private int serverPort;
	
	private String serverCommandQuit;
	
	private String serverCommandRing;
	
	private String serverCommandAddRing;
	
	private String cronCommand;
	
	private String cronTMPFile;
	
	private String cronTag;
	
	/**
	 * Import the default configuration file into this object
	 */
	public void importConf(){
		importConfFromFile(DEFAULT_CONF_FILE);
	}
	
	/**
	 * Import configuration file into this object
	 * @param defaultConfFile The configuration file to import
	 */
	public void importConfFromFile(String defaultConfFile) {
		String content = BasicIO.readFile(defaultConfFile);
		JSONObject js = new JSONObject(content);
		
		JSONObject MQTT = js.getJSONObject("MQTT");
		this.setMQTTServer(MQTT.getString("server"));
		this.setMQTTID(MQTT.getString("id"));
		this.setMQTTRingTopic(MQTT.getString("topic"));
		this.setMQTTRingPayload(MQTT.getString("payload"));
		this.setMQTTRingQOS(MQTT.getInt("QOS"));
		
		JSONObject Server = js.getJSONObject("server");
		this.setServerPort(Server.getInt("port"));
		
		JSONObject Command = Server.getJSONObject("command");
		this.setServerCommandQuit(Command.getString("quitAction"));
		this.setServerCommandRing(Command.getString("ringAction"));
		this.setServerCommandAddRing(Command.getString("addRingAction"));
		
		JSONObject cron = js.getJSONObject("cron");
		this.setCronCommand(cron.getString("command"));
		this.setCronTag(cron.getString("tag"));
		this.setCronTMPFile(cron.getString("tmpFile"));
	}

	/**
	 * Set the MQTT QOS in this object
	 * @param int1 The MQTT QOS to set
	 */
	private void setMQTTRingQOS(int int1) {
		this.MQTTQOS = int1;
	}

	/**
	 * Set the MQTT id uses
	 * @param string The MQTT id
	 */
	public void setMQTTID(String string) {
		this.MQTTID = string;
	}
	
	/**
	 * Get the MQTT id in the configuration file
	 * @return The MQTT id
	 */
	public String getMQTTID(){
		return this.MQTTID;
	}

	/**
	 * Get the values of MQTT server used
	 * @return the values of MQTT server used
	 */
	public String getMQTTServer() {
		return this.MQTTServer;
	}

	/**
	 * Set the value of the MQTT server to use
	 * @param mQTTServer the value of the MQTT server to use
	 */
	public void setMQTTServer(String mQTTServer) {
		this.MQTTServer = mQTTServer;
	}

	/**
	 * Get the MQTT topic to ring event
	 * @return the MQTT topic to ring event
	 */
	public String getMQTTRingTopic() {
		return this.MQTTRingTopic;
	}

	/**
	 * Set the MQTT topic to ring event
	 * @param mQTTRingTopic the MQTT topic to ring event
	 */
	public void setMQTTRingTopic(String mQTTRingTopic) {
		this.MQTTRingTopic = mQTTRingTopic;
	}

	/**
	 * Get the MQTT payload to ring event
	 * @return the MQTT payload to ring event
	 */
	public String getMQTTRingPayload() {
		return this.MQTTRingPayload;
	}

	/**
	 * Set the MQTT payload to ring event
	 * @param mQTTRingPayload the MQTT payload to ring event
	 */
	public void setMQTTRingPayload(String mQTTRingPayload) {
		this.MQTTRingPayload = mQTTRingPayload;
	}

	/**
	 * Get the MQTT QOS uses in this object
	 * @return The MQTT QOS uses
	 */
	public int getMQTTQOS() {
		return this.MQTTQOS;
	}

	/**
	 * @return the serverPort
	 */
	public int getServerPort() {
		return this.serverPort;
	}

	/**
	 * @param serverPort the serverPort to set
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * @return the serverCommandQuit
	 */
	public String getServerCommandQuit() {
		return this.serverCommandQuit;
	}

	/**
	 * @param serverCommandQuit the serverCommandQuit to set
	 */
	public void setServerCommandQuit(String serverCommandQuit) {
		this.serverCommandQuit = serverCommandQuit;
	}

	/**
	 * @return the serverCommandAddRing
	 */
	public String getServerCommandAddRing() {
		return this.serverCommandAddRing;
	}

	/**
	 * @param serverCommandAddRing the serverCommandAddRing to set
	 */
	public void setServerCommandAddRing(String serverCommandAddRing) {
		this.serverCommandAddRing = serverCommandAddRing;
	}

	/**
	 * @return the serverCommandRing
	 */
	public String getServerCommandRing() {
		return this.serverCommandRing;
	}

	/**
	 * @param serverCommandRing the serverCommandRing to set
	 */
	public void setServerCommandRing(String serverCommandRing) {
		this.serverCommandRing = serverCommandRing;
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
	 * @return the cronTMPFile
	 */
	public String getCronTMPFile() {
		return this.cronTMPFile;
	}

	/**
	 * @param cronTMPFile the cronTMPFile to set
	 */
	public void setCronTMPFile(String cronTMPFile) {
		this.cronTMPFile = cronTMPFile;
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
	 * @param mQTTQOS the mQTTQOS to set
	 */
	public void setMQTTQOS(int mQTTQOS) {
		this.MQTTQOS = mQTTQOS;
	}

}
