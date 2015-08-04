package fr.utbm.to52.smarthome.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

/**
 * 
 * @author alexandre
 *
 */
public class Conf {

	private static final String DEFAULT_CONF_FILE = "smart.conf";
	
	private String MQTTServer;

	private String MQTTRingTopic;

	private String MQTTRingPayload;

	public Conf(){
		
	}
	
	public static String readFile(String file){
		File f = new File(file);
		String content = "";
		
		if(f.exists() && f.canRead()){
			try {
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
	
	public void importConf(){
		importConfFromFile(DEFAULT_CONF_FILE);
	}
	
	public void importConfFromFile(String defaultConfFile) {
		String content = readFile(defaultConfFile);
		JSONObject js = new JSONObject(content);
		JSONObject MQTT = js.getJSONObject("MQTT");
		this.setMQTTServer(MQTT.getString("server"));
		this.setMQTTRingTopic(MQTT.getString("topic"));
		this.setMQTTRingPayload(MQTT.getString("payload"));
	}

	public String getMQTTServer() {
		return this.MQTTServer;
	}

	public void setMQTTServer(String mQTTServer) {
		this.MQTTServer = mQTTServer;
	}

	public String getMQTTRingTopic() {
		return this.MQTTRingTopic;
	}

	public void setMQTTRingTopic(String mQTTRingTopic) {
		this.MQTTRingTopic = mQTTRingTopic;
	}

	public String getMQTTRingPayload() {
		return this.MQTTRingPayload;
	}

	public void setMQTTRingPayload(String mQTTRingPayload) {
		this.MQTTRingPayload = mQTTRingPayload;
	}

}
