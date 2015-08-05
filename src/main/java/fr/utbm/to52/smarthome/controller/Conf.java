package fr.utbm.to52.smarthome.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

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
	
	/**
	 * Read a file passed on argument
	 * @param file The file to read
	 * @return String of the file
	 */
	public static String readFile(String file){
		File f = new File(file);
		String content = "";
		
		if(f.exists() && f.canRead()){
			try {
				@SuppressWarnings("resource")
				BufferedReader reader = new BufferedReader(new FileReader(f));
				
				String line = null;
				do{
					line = reader.readLine();
					if(line != null)
						content += line;
				}while(line != null);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return content;
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
		String content = readFile(defaultConfFile);
		JSONObject js = new JSONObject(content);
		JSONObject MQTT = js.getJSONObject("MQTT");
		this.setMQTTServer(MQTT.getString("server"));
		this.setMQTTID(MQTT.getString("id"));
		this.setMQTTRingTopic(MQTT.getString("topic"));
		this.setMQTTRingPayload(MQTT.getString("payload"));
		this.setMQTTRingQOS(MQTT.getInt("QOS"));
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

}
