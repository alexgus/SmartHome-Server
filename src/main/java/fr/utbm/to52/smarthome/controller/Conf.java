package fr.utbm.to52.smarthome.controller;

import java.util.ArrayList;
import java.util.List;

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
	
	private List<String> featuresEnabled;
	
	private ClockFeature clockfeature;

	//-------- Components
	
	private String clockTopic;
	private String clockAbort;
	private String clockRing;
	
	private String lightTopic;
	
	private String blindTopic;
	private String blindOpen;
	private String blindClose;
	
	private String bedTopic;
	private String bedIn;
	private String bedOut;
	
	private String motionTopic;
	private String motionIn;
	private String motionOut;
	
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
		if(this.featuresEnabled.contains("clock"))
			this.clockfeature = new ClockFeature(featuresConfig.getJSONObject("clock"));
		
		JSONObject cpClock = js.getJSONObject("componentClock");
		this.clockTopic = cpClock.getString("topic");
		this.clockRing = cpClock.getString("commandRing");
		this.clockAbort = cpClock.getString("commandClose");
		
		JSONObject cpLight = js.getJSONObject("componentLight");
		this.setLightTopic(cpLight.getString("topic"));
		
		JSONObject cpBlind = js.getJSONObject("componentBlind");
		this.blindTopic = cpBlind.getString("topic");
		this.blindClose = cpBlind.getString("commandClose");
		this.blindOpen = cpBlind.getString("commandOpen");
		
		JSONObject cpBed = js.getJSONObject("componentBed");
		this.bedTopic = cpBed.getString("topic");
		this.bedIn = cpBed.getString("commandIn");
		this.bedOut = cpBed.getString("commandOut");
		
		JSONObject cpMotion = js.getJSONObject("componentMotion");
		this.motionTopic = cpMotion.getString("topic");
		this.motionIn = cpMotion.getString("motionIn");
		this.motionOut = cpMotion.getString("motionOut");
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
	 * Get the MQTT QOS uses in this object
	 * @return The MQTT QOS uses
	 */
	public int getMQTTQOS() {
		return this.MQTTQOS;
	}

	/**
	 * @param mQTTQOS the mQTTQOS to set
	 */
	public void setMQTTQOS(int mQTTQOS) {
		this.MQTTQOS = mQTTQOS;
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
	 * @return the clockTopic
	 */
	public String getClockTopic() {
		return this.clockTopic;
	}

	/**
	 * @param clockTopic the clockTopic to set
	 */
	public void setClockTopic(String clockTopic) {
		this.clockTopic = clockTopic;
	}

	/**
	 * @return the clockAbort
	 */
	public String getClockAbort() {
		return this.clockAbort;
	}

	/**
	 * @param clockAbort the clockAbort to set
	 */
	public void setClockAbort(String clockAbort) {
		this.clockAbort = clockAbort;
	}

	/**
	 * @return the clockRing
	 */
	public String getClockRing() {
		return this.clockRing;
	}

	/**
	 * @param clockRing the clockRing to set
	 */
	public void setClockRing(String clockRing) {
		this.clockRing = clockRing;
	}

	/**
	 * @return the blindTopic
	 */
	public String getBlindTopic() {
		return this.blindTopic;
	}

	/**
	 * @param blindTopic the blindTopic to set
	 */
	public void setBlindTopic(String blindTopic) {
		this.blindTopic = blindTopic;
	}

	/**
	 * @return the blindOpen
	 */
	public String getBlindOpen() {
		return this.blindOpen;
	}

	/**
	 * @param blindOpen the blindOpen to set
	 */
	public void setBlindOpen(String blindOpen) {
		this.blindOpen = blindOpen;
	}

	/**
	 * @return the blindClose
	 */
	public String getBlindClose() {
		return this.blindClose;
	}

	/**
	 * @param blindClose the blindClose to set
	 */
	public void setBlindClose(String blindClose) {
		this.blindClose = blindClose;
	}

	/**
	 * @return the bedTopic
	 */
	public String getBedTopic() {
		return this.bedTopic;
	}

	/**
	 * @param bedTopic the bedTopic to set
	 */
	public void setBedTopic(String bedTopic) {
		this.bedTopic = bedTopic;
	}

	/**
	 * @return the bedIn
	 */
	public String getBedIn() {
		return this.bedIn;
	}

	/**
	 * @param bedIn the bedIn to set
	 */
	public void setBedIn(String bedIn) {
		this.bedIn = bedIn;
	}

	/**
	 * @return the bedOut
	 */
	public String getBedOut() {
		return this.bedOut;
	}

	/**
	 * @param bedOut the bedOut to set
	 */
	public void setBedOut(String bedOut) {
		this.bedOut = bedOut;
	}

	/**
	 * @return the motionTopic
	 */
	public String getMotionTopic() {
		return this.motionTopic;
	}

	/**
	 * @param motionTopic the motionTopic to set
	 */
	public void setMotionTopic(String motionTopic) {
		this.motionTopic = motionTopic;
	}

	/**
	 * @return the motionIn
	 */
	public String getMotionIn() {
		return this.motionIn;
	}

	/**
	 * @param motionIn the motionIn to set
	 */
	public void setMotionIn(String motionIn) {
		this.motionIn = motionIn;
	}

	/**
	 * @return the motionOut
	 */
	public String getMotionOut() {
		return this.motionOut;
	}

	/**
	 * @param motionOut the motionOut to set
	 */
	public void setMotionOut(String motionOut) {
		this.motionOut = motionOut;
	}

	/**
	 * @return the lightTopic
	 */
	public String getLightTopic() {
		return this.lightTopic;
	}

	/**
	 * @param lightTopic the lightTopic to set
	 */
	public void setLightTopic(String lightTopic) {
		this.lightTopic = lightTopic;
	}
	
}
