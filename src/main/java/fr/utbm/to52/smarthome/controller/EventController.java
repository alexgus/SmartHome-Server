/**
 * 
 */
package fr.utbm.to52.smarthome.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lightcouch.CouchDbClient;

import fr.utbm.to52.smarthome.controller.events.RingEvent;
import fr.utbm.to52.smarthome.controller.events.core.Event;
import fr.utbm.to52.smarthome.services.com.MQTTService;
import fr.utbm.to52.smarthome.services.couchdb.CouchdbService;

/**
 * @author Alexandre Guyon
 *
 */
public class EventController {
	
	private Controller controller;
	
	private List<Event> lEvent;
	
	private Map<String, Event> triggerable;
	
	/**
	 * @param c Initialize with controller
	 */
	public EventController(Controller c) {
		this.controller = c;
		this.lEvent = new LinkedList<>();
	}
	
	public void initialize(JSONArray js){
		// ClassLoader classloader = EventController.class.getClassLoader();
		
		for(int i = 0 ; i < js.length() ; ++i){
			JSONObject map = js.getJSONObject(i);
			
			// Other solution
			
			// 	  Defines outEvent and trigger it 
			
			
			Event in = this.getEvent(map.getString("inEvent"));
			
			JSONObject inConf = map.getJSONObject("config");
			if(inConf.has("command"))
				this.triggerable.put(inConf.getString("command"), in);
			
			/*try{
				Class<?> inClass = classloader.loadClass("fr.utbm.to52.smarthome.controller.events." + map.getString("inEvent"));
				Constructor<?>[] t = inClass.getConstructors();
				for(int j = 0 ; j < t.length ; ++j){
					System.out.println(t[j]);
					for(int k = 0 ; k < t[j].getParameterTypes().length ; ++k){
						System.out.println(t[j].getParameterTypes()[k]);
						// TODO search corresponding in services
					}
					// t[j].newInstance(7,"fred");
					// FIXME respect order in arguments
					// FIXME Add annotation on DAO arguments (DAO : NoteDAO , etc)
				}
				
			}catch(ClassNotFoundException | SecurityException | IllegalArgumentException e){
				e.printStackTrace();
			}*/
			
			
		}
	}

	private Event getEvent(String eventName) {
		Event ev = null;
		switch (eventName) {
			case "RingEvent":
				ev = new RingEvent(((CouchdbService)this.controller.getService(CouchdbService.class)).getSession(),
						((MQTTService)this.controller.getService(MQTTService.class)).getMqtt());
				break;
		}
		return ev;
	}
}
