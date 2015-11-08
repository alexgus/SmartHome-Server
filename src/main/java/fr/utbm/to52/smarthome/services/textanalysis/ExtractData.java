/**
 * 
 */
package fr.utbm.to52.smarthome.services.textanalysis;

/**
 * @author Alexandre Guyon
 *
 */
public abstract class ExtractData<D> {

	protected String text;
	
	/**
	 * 
	 */
	public ExtractData(String t) {
		this.text = t;
	}
	
	public abstract D getData();

}
