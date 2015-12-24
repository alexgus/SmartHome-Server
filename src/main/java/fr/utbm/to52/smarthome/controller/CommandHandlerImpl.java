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

import fr.utbm.to52.smarthome.controller.events.core.Event;

/**
 * @author Alexandre Guyon
 *
 */
public class CommandHandlerImpl implements CommandHandler, MqttCallback, Runnable{

	private static final int TIME_CHECK = 100;

	private Controller controller;

	private Collection<Message> lCmd = Collections.synchronizedCollection(new ArrayList<Message>());

	private Event noSuchCommand;

	private Event ringEvent;

	private Event addRingEvent;

	private Event lightEvent;

	private Event quitEvent;

	private Event addNote;

	private Event getNote;

	private Event getLogBook;

	private Event motionSensor;
	
	private Event presence;
	
	private Event abort;
	
	private Event shutter;

	/**
	 * Create command handler with
	 * @param c Controller to handle
	 */
	public CommandHandlerImpl(Controller c) {
		this.controller = c;
	}

	@Override
	public void run() {
		List<Message> handledCommand = new ArrayList<>();

		while(this.controller.isRunning()){
			synchronized (this.lCmd) {
				for (Message cmd : this.lCmd) {
					this.handleQueuedCmd(cmd.getSubject(), cmd.getMessage());
					handledCommand.add(cmd);
				}
			}

			for (Message cmdOk : handledCommand)
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
	public synchronized void handle(String subject, String cmd){
		Message m = new Message(subject, cmd);
		this.lCmd.add(m);
	}

	private void handleQueuedCmd(String subject, String cmd) { // FIXME equals or contains
		if(cmd.equals(Conf.getInstance().getClockRing())){
			if(this.ringEvent != null)
				this.ringEvent.inform(null);
			if(this.lightEvent != null)
				this.lightEvent.inform(null);
		}else if(cmd.contains("AddRing")){ // TODO conf
			if(this.addRingEvent != null)
				this.addRingEvent.inform(cmd);
		}else if(cmd.contains(Conf.getInstance().getMotionOut())){
			if(this.motionSensor != null)
				this.motionSensor.inform(null);
		}else if(cmd.contains("out")){ // presence
			if(this.presence != null)
				this.presence.inform(null);
		}else if(cmd.equals("QUIT")){ // TODO conf
			this.quitEvent.inform(null);
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
		//if(arg0.equals(Conf.getInstance().getMQTTRingTopic()))
			this.handle(arg0.toString(), arg1.toString());
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

	/**
	 * @return the motionSensor
	 */
	public Event getMotionSensor() {
		return this.motionSensor;
	}

	/**
	 * @param motionSensor the motionSensor to set
	 */
	public void setMotionSensor(Event motionSensor) {
		this.motionSensor = motionSensor;
	}

	/**
	 * @return the presence
	 */
	public Event getPresence() {
		return this.presence;
	}

	/**
	 * @param presence the presence to set
	 */
	public void setPresence(Event presence) {
		this.presence = presence;
	}

	/**
	 * @return the abort
	 */
	public Event getAbort() {
		return this.abort;
	}

	/**
	 * @param abort the abort to set
	 */
	public void setAbort(Event abort) {
		this.abort = abort;
	}

	/**
	 * @return the shutter
	 */
	public Event getShutter() {
		return this.shutter;
	}

	/**
	 * @param shutter the shutter to set
	 */
	public void setShutter(Event shutter) {
		this.shutter = shutter;
	}

}
