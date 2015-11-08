/**
 * 
 */
package fr.utbm.to52.smarthome.services.textanalysis;

import java.util.Date;

/**
 * @author Alexandre Guyon
 *
 */
public class ExctractDate extends ExtractData<Date> {
	
	public ExctractDate(String t) {
		super(t);
	}

	@Override
	public Date getData() {
		String[] t = this.text.split(" ");
		
		for (String word : t) {
			System.out.println(word);
			int nb;
			try{
				nb = Integer.parseInt(word);
				System.out.println("Found : " + nb);
			}catch(NumberFormatException e){
				nb = 0;
			}
		}
		
		return new Date();
	}

	public static void main(String[] args){
		ExctractDate p = new ExctractDate("Bonjour,\n je voudrais prendre RDV avec vous le 10 novembre à 10h\n\nCordialement,\nAlexandre Guyon");
		
		System.out.println(p.getData());
	}

}
