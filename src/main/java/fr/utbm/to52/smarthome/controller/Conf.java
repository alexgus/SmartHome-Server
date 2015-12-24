package fr.utbm.to52.smarthome.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.utbm.to52.smarthome.controller.conf.ClockFeature;
import fr.utbm.to52.smarthome.util.BasicIO;
 
/**
 * This class is used to parse the configuration file.
 * This is also used to store all this values.
 * @author Alexandre Guyon
 *
 */
public class Conf {

	/**
	 * Singleton instance for this configuration 
	 */
	private static Conf singleton = null;

	/**
	 * Get an instance from the configuration. The first call initialize it with the default configuration file.
	 * @return Singleton instance of this Configuration
	 */
	public static Conf getInstance(){
		if(singleton == null)
			singleton = new Conf();
		return singleton;
	}
	
	/**
	 * Defining the source of the command is from network. tag configuration
	 */
	public static final int SOURCE_NET = 0;
	
	/**
	 * Defining the source of the command is from ICAL. tagICal configuration
	 */
	public static final int SOURCE_ICAL = 1;
	
	/**
	 * Default config file (old one)
	 */
	private static final String DEFAULT_CONF_FILE_old = "smart.conf.old";
	
	/**
	 * New config file
	 */
	private static final String DEFAULT_CONF_FILE = "smart.conf";
	
	/**
	 * Use system cron (UNIX/LINUX/MACOS only)
	 */
	public static final String CRON_SYSTEM = "system";
	
	/**
	 * Use java cron (Multiplatform)
	 */
	public static final String CRON_JAVA = "java";	
	
	private String MQTTServer;
	
	private String MQTTID;
	
	private int MQTTQOS;
	
	private String googleApiKey;
	
	private String googleApiSecret;
	
	private String googleScope;
	
	///////////////////////////////////////
	private List<String> featuresEnabled;
	
	private ClockFeature clockfeature;
	
	private Map<String, JSONArray> featuresMap;
	///////////////////////////////////////
	
	private String MQTTRingTopic;
	
	private String MQTTLightTopic;
	
	private String MQTTShutterTopic;

	private String MQTTRingPayload;
	
	private String MQTTAbortPayload;
	
	private int serverPort;
	
	private String commandQuit;
	
	private String commandRing;
	
	private String commandAddRing;
	
	private String commandAddNote;
	
	private String commandGetNote;
	
	private String commandGetLogBook;
	
	private String commandMotionSensor;
	
	private String commandShutter;
	
	private String cronSource;
	
	private String cronCommand;
	
	private String cronTMPFile;
	
	private String cronTag;
	
	private String cronICalTag;
	
	private String alarmWakeUpTime;
	
	private List<String> alarmURL;
	
	private String topicAnswerSuffix;

	/**
	 * Private constructor --> singleton
	 */
	private Conf() {
		// nothing
	}
	
	/**
	 * Import the default configuration file into this object
	 */
	public void importConf(){
		importOldConfFromFile(DEFAULT_CONF_FILE_old);
		importConfFromFile(DEFAULT_CONF_FILE);
	}
	
	/**
	 * Import configuration file into this object
	 * @param defaultConfFile The configuration file to import
	 */
	public void importConfFromFile(String defaultConfFile) {
		String content = BasicIO.readFile(defaultConfFile);
		JSONObject js = new JSONObject(content);
		
		JSONObject network = js.getJSONObject("network");
		JSONObject MQTT = network.getJSONObject("MQTT");
		this.setMQTTServer(MQTT.getString("server"));
		this.setMQTTID(MQTT.getString("id"));
		this.setMQTTQOS(MQTT.getInt("QOS"));
		
		JSONObject connector = js.getJSONObject("connector");
		JSONObject google = connector.getJSONObject("google");
		this.setGoogleApiKey(google.getString("apiKey"));
		this.setGoogleApiSecret(google.getString("apiSecret"));
		this.setGoogleScope(google.getString("scope"));
		
		JSONArray ftenabled = js.getJSONArray("featuresEnabled");
		this.featuresEnabled = new ArrayList<>();
		for(int i = 0 ; i < ftenabled.length(); ++i)
			this.featuresEnabled.add(ftenabled.getString(i));
		
		JSONObject featuresConfig = js.getJSONObject("featuresConfig");
		JSONObject featuresM = js.getJSONObject("featuresMap");
		this.featuresMap = new HashMap<>();
		if(this.featuresEnabled.contains("clock")){
			this.clockfeature = new ClockFeature(featuresConfig.getJSONObject("clock"));
			this.featuresMap.put("clock", featuresM.getJSONArray("clock"));
		}
	}

	/**
	 * Import configuration file into this object
	 * @param defaultConfFile The configuration file to import
	 */
	@Deprecated
	public void importOldConfFromFile(String defaultConfFile) {
		String content = BasicIO.readFile(defaultConfFile);
		JSONObject js = new JSONObject(content);
		
		JSONObject MQTT = js.getJSONObject("MQTT");
		this.setMQTTServer(MQTT.getString("server"));
		this.setMQTTID(MQTT.getString("id"));
		this.setMQTTRingTopic(MQTT.getString("topic"));
		this.setMQTTLightTopic(MQTT.getString("topicLight"));
		this.setMQTTShutterTopic(MQTT.getString("shutterTopic"));
		this.setMQTTRingPayload(MQTT.getString("payload"));
		this.setMQTTAbortPayload(MQTT.getString("abort"));
		this.setMQTTRingQOS(MQTT.getInt("QOS"));
		
		JSONObject Server = js.getJSONObject("server");
		this.setServerPort(Server.getInt("port"));
		
		JSONObject Command = js.getJSONObject("command");
		this.setTopicAnswerSuffix("topicAnswerSuffix");
		this.setCommandQuit(Command.getString("quitAction"));
		this.setCommandRing(Command.getString("ringAction"));
		this.setCommandAddRing(Command.getString("addRingAction"));
		this.setCommandAddNote(Command.getString("addNoteAction"));
		this.setCommandGetNote(Command.getString("getNoteAction"));
		this.setCommandGetLogBook(Command.getString("getLogBookAction"));
		this.setCommandMotionSensor(Command.getString("motionSensorAction"));
		this.setCommandShutter(Command.getString("shutterAction"));
		
		JSONObject cron = js.getJSONObject("cron");
		this.setCronSource(cron.getString("source"));
		this.setCronCommand(cron.getString("command"));
		this.setCronTag(cron.getString("tag"));
		this.setCronICalTag(cron.getString("icalTag"));
		this.setCronTMPFile(cron.getString("tmpFile"));
		
		JSONObject alarm = js.getJSONObject("alarm");
		this.setAlarmWakeUpTime(alarm.getString("wakeUpTimeBeforeEvent"));
		this.alarmURL = new LinkedList<>();
		JSONArray jsArr = alarm.getJSONArray("calendar");
		for(int i = 0 ; i < jsArr.length() ; ++i)
			this.alarmURL.add(jsArr.getString(i));
		
		JSONObject apiGoogle = js.getJSONObject("apiGoogle");
		this.setGoogleApiKey(apiGoogle.getString("apiKey"));
		this.setGoogleApiSecret(apiGoogle.getString("apiSecret"));
		this.setGoogleScope(apiGoogle.getString("scope"));
	}

	/**
	 * Set the MQTT QOS in this object
	 * @param int1 The MQTT QOS to set
	 */
	private void setMQTTRingQOS(int int1) { // TODO Refactor -> rename
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
	@Deprecated
	public String getMQTTRingTopic() {
		return this.MQTTRingTopic;
	}

	/**
	 * Set the MQTT topic to ring event
	 * @param mQTTRingTopic the MQTT topic to ring event
	 */
	@Deprecated
	public void setMQTTRingTopic(String mQTTRingTopic) {
		this.MQTTRingTopic = mQTTRingTopic;
	}

	/**
	 * @return the mQTTLightTopic
	 */
	@Deprecated
	public String getMQTTLightTopic() {
		return this.MQTTLightTopic;
	}

	/**
	 * @param mQTTLightTopic the mQTTLightTopic to set
	 */
	@Deprecated
	public void setMQTTLightTopic(String mQTTLightTopic) {
		this.MQTTLightTopic = mQTTLightTopic;
	}

	/**
	 * Get the MQTT payload to ring event
	 * @return the MQTT payload to ring event
	 */
	@Deprecated
	public String getMQTTRingPayload() {
		return this.MQTTRingPayload;
	}

	/**
	 * Set the MQTT payload to ring event
	 * @param mQTTRingPayload the MQTT payload to ring event
	 */
	@Deprecated
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
	@Deprecated
	public int getServerPort() {
		return this.serverPort;
	}

	/**
	 * @param serverPort the serverPort to set
	 */
	@Deprecated
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * @return the serverCommandQuit
	 */
	@Deprecated
	public String getCommandQuit() {
		return this.commandQuit;
	}

	/**
	 * @param serverCommandQuit the serverCommandQuit to set
	 */
	@Deprecated
	public void setCommandQuit(String serverCommandQuit) {
		this.commandQuit = serverCommandQuit;
	}

	/**
	 * @return the serverCommandAddRing
	 */
	@Deprecated
	public String getCommandAddRing() {
		return this.commandAddRing;
	}

	/**
	 * @param serverCommandAddRing the serverCommandAddRing to set
	 */
	@Deprecated
	public void setCommandAddRing(String serverCommandAddRing) {
		this.commandAddRing = serverCommandAddRing;
	}

	/**
	 * @return the serverCommandRing
	 */
	@Deprecated
	public String getCommandRing() {
		return this.commandRing;
	}

	/**
	 * @param serverCommandRing the serverCommandRing to set
	 */
	@Deprecated
	public void setCommandRing(String serverCommandRing) {
		this.commandRing = serverCommandRing;
	}

	/**
	 * @return the cronCommand
	 */
	@Deprecated
	public String getCronCommand() {
		return this.cronCommand;
	}

	/**
	 * @param cronCommand the cronCommand to set
	 */
	@Deprecated
	public void setCronCommand(String cronCommand) {
		this.cronCommand = cronCommand;
	}

	/**
	 * @return the cronTMPFile
	 */
	@Deprecated
	public String getCronTMPFile() {
		return this.cronTMPFile;
	}

	/**
	 * @param cronTMPFile the cronTMPFile to set
	 */
	@Deprecated
	public void setCronTMPFile(String cronTMPFile) {
		this.cronTMPFile = cronTMPFile;
	}

	/**
	 * @return the cronTag
	 */
	@Deprecated
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

	/**
	 * @return the alarmWakeUpTime
	 */
	@Deprecated
	public String getAlarmWakeUpTime() {
		return this.alarmWakeUpTime;
	}

	/**
	 * @param alarmWakeUpTime the alarmWakeUpTime to set
	 */
	@Deprecated
	public void setAlarmWakeUpTime(String alarmWakeUpTime) {
		this.alarmWakeUpTime = alarmWakeUpTime;
	}

	/**
	 * @return the alarmURL
	 */
	@Deprecated
	public List<String> getAlarmURL() {
		return this.alarmURL;
	}

	/**
	 * @param alarmURL the alarmURL to set
	 */
	@Deprecated
	public void setAlarmURL(List<String> alarmURL) {
		this.alarmURL = alarmURL;
	}

	/**
	 * @return the cronICalTag
	 */
	@Deprecated
	public String getCronICalTag() {
		return this.cronICalTag;
	}

	/**
	 * @param cronICalTag the cronICalTag to set
	 */
	@Deprecated
	public void setCronICalTag(String cronICalTag) {
		this.cronICalTag = cronICalTag;
	}

	/**
	 * @return the cronSource
	 */
	@Deprecated
	public String getCronSource() {
		return this.cronSource;
	}

	/**
	 * @param cronSource the cronSource to set
	 */
	@Deprecated
	public void setCronSource(String cronSource) {
		this.cronSource = cronSource;
	}

	/**
	 * @return the googleApiKey
	 */
	public String getGoogleApiKey() {
		return this.googleApiKey;
	}

	/**
	 * @param googleApiKey the googleApiKey to set
	 */
	public void setGoogleApiKey(String googleApiKey) {
		this.googleApiKey = googleApiKey;
	}

	/**
	 * @return the googleApiSecret
	 */
	public String getGoogleApiSecret() {
		return this.googleApiSecret;
	}

	/**
	 * @param googleApiSecret the googleApiSecret to set
	 */
	public void setGoogleApiSecret(String googleApiSecret) {
		this.googleApiSecret = googleApiSecret;
	}

	/**
	 * @return the googleScope
	 */
	public String getGoogleScope() {
		return this.googleScope;
	}

	/**
	 * @param googleScope the googleScope to set
	 */
	public void setGoogleScope(String googleScope) {
		this.googleScope = googleScope;
	}

	/**
	 * @return the commandAddNote
	 */
	@Deprecated
	public String getCommandAddNote() {
		return this.commandAddNote;
	}

	/**
	 * @param commandAddNote the commandAddNote to set
	 */
	@Deprecated
	public void setCommandAddNote(String commandAddNote) {
		this.commandAddNote = commandAddNote;
	}

	/**
	 * @return the commandGetNote
	 */
	@Deprecated
	public String getCommandGetNote() {
		return this.commandGetNote;
	}

	/**
	 * @param commandGetNote the commandGetNote to set
	 */
	@Deprecated
	public void setCommandGetNote(String commandGetNote) {
		this.commandGetNote = commandGetNote;
	}
	
	/**
	 * @return the topicAnswerSuffix
	 */
	@Deprecated
	public String getTopicAnswerSuffix() {
		return this.topicAnswerSuffix;
	}

	/**
	 * @param topicAnswerSuffix the topicAnswerSuffix to set
	 */
	@Deprecated
	public void setTopicAnswerSuffix(String topicAnswerSuffix) {
		this.topicAnswerSuffix = topicAnswerSuffix;
	}

	/**
	 * @return the commandGetLogBook
	 */
	@Deprecated
	public String getCommandGetLogBook() {
		return this.commandGetLogBook;
	}

	/**
	 * @param commandGetLogBook the commandGetLogBook to set
	 */
	@Deprecated
	public void setCommandGetLogBook(String commandGetLogBook) {
		this.commandGetLogBook = commandGetLogBook;
	}

	/**
	 * @return the mQTTAbortPayload
	 */
	@Deprecated
	public String getMQTTAbortPayload() {
		return this.MQTTAbortPayload;
	}

	/**
	 * @param mQTTAbortPayload the mQTTAbortPayload to set
	 */
	@Deprecated
	public void setMQTTAbortPayload(String mQTTAbortPayload) {
		this.MQTTAbortPayload = mQTTAbortPayload;
	}

	/**
	 * @return the commandMotionSensor
	 */
	@Deprecated
	public String getCommandMotionSensor() {
		return this.commandMotionSensor;
	}

	/**
	 * @param commandMotionSensor the commandMotionSensor to set
	 */
	@Deprecated
	public void setCommandMotionSensor(String commandMotionSensor) {
		this.commandMotionSensor = commandMotionSensor;
	}

	/**
	 * @return the commandShutter
	 */
	@Deprecated
	public String getCommandShutter() {
		return this.commandShutter;
	}

	/**
	 * @param commandShutter the commandShutter to set
	 */
	@Deprecated
	public void setCommandShutter(String commandShutter) {
		this.commandShutter = commandShutter;
	}

	/**
	 * @return the mQTTShutterTopic
	 */
	@Deprecated
	public String getMQTTShutterTopic() {
		return this.MQTTShutterTopic;
	}

	/**
	 * @param mQTTShutterTopic the mQTTShutterTopic to set
	 */
	@Deprecated
	public void setMQTTShutterTopic(String mQTTShutterTopic) {
		this.MQTTShutterTopic = mQTTShutterTopic;
	}

	/**
	 * @return the featuresEnabled
	 */
	public List<String> getFeaturesEnabled() {
		return this.featuresEnabled;
	}

	/**
	 * @param featuresEnabled the featuresEnabled to set
	 */
	public void setFeaturesEnabled(List<String> featuresEnabled) {
		this.featuresEnabled = featuresEnabled;
	}

	/**
	 * @return the clockfeature
	 */
	public ClockFeature getClockfeature() {
		return this.clockfeature;
	}

	/**
	 * @param clockfeature the clockfeature to set
	 */
	public void setClockfeature(ClockFeature clockfeature) {
		this.clockfeature = clockfeature;
	}

	/**
	 * @return the featuresMap
	 */
	public Map<String, JSONArray> getFeaturesMap() {
		return this.featuresMap;
	}
	
	
}
