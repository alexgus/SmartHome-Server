/**
 * 
 */
package fr.utbm.to52.smarthome.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import fr.utbm.to52.smarthome.controller.events.Event;

/**
 * @author Alexandre Guyon
 *
 */
public class CommandHandlerImpl implements CommandHandler, MqttCallback, Runnable{
	
	private static final int TIME_CHECK = 100;
	
	private Controller controller;

	private Collection<String> lCmd = Collections.synchronizedCollection(new ArrayList<String>());
	
	private Event noSuchCommand;
	
	private Event ringEvent;
	
	private Event addRingEvent;
	
	private Event lightEvent;
	
	private Event quitEvent;
	
	private Event addNote;
	
	private Event getNote;
	
	private Event getLogBook;

	/**
	 * Create command handler with
	 * @param c Controller to handle
	 */
	public CommandHandlerImpl(Controller c) {
		this.controller = c;
	}
	
	@Override
	public void run() {
		List<String> handledCommand = new ArrayList<>();
		
		while(this.controller.isRunning()){
			synchronized (this.lCmd) {
				for (String cmd : this.lCmd) {
					this.handleQueuedCmd(new String(cmd));
					handledCommand.add(cmd);
				}
			}
			
			for (String cmdOk : handledCommand)
				this.lCmd.remove(cmdOk);
			
			try {
				Thread.sleep(CommandHandlerImpl.TIME_CHECK);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.controller.CommandHandler#handle(java.lang.String)
	 */
	@Override
	public synchronized void handle(String cmd){
		this.lCmd.add(cmd);
	}
	
	
	private void handleQueuedCmd(String cmd) {
		if(cmd.equals(Conf.getInstance().getCommandRing())){
			if(this.getRingEvent() != null)
				this.getRingEvent().inform(null);
			if(this.getLightEvent() != null)
				this.getLightEvent().inform(null);
		}else if(cmd.equals(Conf.getInstance().getCommandQuit())){
			this.quitEvent.inform(null);
		}else if(cmd.contains(Conf.getInstance().getCommandAddRing())){
			if(this.getAddRingEvent() != null)
				this.getAddRingEvent().inform(cmd);
		}else if(cmd.contains(Conf.getInstance().getCommandAddNote())){
			if(this.getAddNote() != null)
				this.getAddNote().inform(cmd);
		}else if(cmd.contains(Conf.getInstance().getCommandGetNote())){
			if(this.getGetNote() != null)
				this.getGetNote().inform(cmd);
		}else if(cmd.contains(Conf.getInstance().getCommandGetLogBook())){
			if(this.getGetLogBook() != null)
				this.getGetLogBook().inform(cmd);
		}else{
			this.noSuchCommand.inform(cmd);
		}
	}

	@Override
	public void connectionLost(Throwable arg0) {
		System.err.println(arg0.getMessage());
		arg0.printStackTrace();
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// Nothing
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		if(arg0.equals(Conf.getInstance().getMQTTRingTopic()))
			this.handle(arg1.toString());
	}
	
	/**
	 * Get the add ring event set to this handler
	 * @return The add ring event set
	 */
	public Event getAddRingEvent() {
		return this.addRingEvent;
	}
	
	/**
	 * Set the add ring event
	 * @param r The add ring event
	 */
	public void setAddRingEventController(Event r){
		this.addRingEvent = r;
	}

	/**
	 * Set the ring event 
	 * @param r The ring event
	 */
	public void setRingEventController(Event r){
		this.ringEvent = r;
	}
	
	/**
	 * Get the ring event for this handler
	 * @return The ring event set
	 */
	public Event getRingEvent(){
		return this.ringEvent;
	}

	/**
	 * @return the lightEvent
	 */
	public Event getLightEvent() {
		return this.lightEvent;
	}

	/**
	 * @param lightEvent the lightEvent to set
	 */
	public void setLightEvent(Event lightEvent) {
		this.lightEvent = lightEvent;
	}

	/**
	 * @return the quitEvent
	 */
	public Event getQuitEvent() {
		return this.quitEvent;
	}

	/**
	 * @param quitEvent the quitEvent to set
	 */
	public void setQuitEvent(Event quitEvent) {
		this.quitEvent = quitEvent;
	}

	/**
	 * @return the noSuchCommand
	 */
	public Event getNoSuchCommand() {
		return this.noSuchCommand;
	}

	/**
	 * @param noSuchCommand the noSuchCommand to set
	 */
	public void setNoSuchCommand(Event noSuchCommand) {
		this.noSuchCommand = noSuchCommand;
	}

	/**
	 * @return the addNote
	 */
	public Event getAddNote() {
		return this.addNote;
	}

	/**
	 * @param addNote the addNote to set
	 */
	public void setAddNote(Event addNote) {
		this.addNote = addNote;
	}

	/**
	 * @return the getNote
	 */
	public Event getGetNote() {
		return this.getNote;
	}

	/**
	 * @param getNote the getNote to set
	 */
	public void setGetNote(Event getNote) {
		this.getNote = getNote;
	}

	/**
	 * @return the getLogBook
	 */
	public Event getGetLogBook() {
		return this.getLogBook;
	}

	/**
	 * @param getLogBook the getLogBook to set
	 */
	public void setGetLogBook(Event getLogBook) {
		this.getLogBook = getLogBook;
	}

}
