package fr.utbm.to52.smarthome;

import fr.utbm.to52.smarthome.controller.Controller;

public class Launch {

	public static void main(String[] args){
		Controller.getInstance().start();
	}
}
