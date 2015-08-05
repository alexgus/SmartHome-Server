/**
 * 
 */
package fr.utbm.to52.smarthome.model;

import it.sauronsoftware.cron4j.TaskTable;

/**
 * Redefined TaskTable for ToString support
 * 
 * @author Alexandre Guyon
 *
 */
public class MyTaskTable extends TaskTable {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "";
		
		String raw;
		for(int i = 0 ; i < this.size() ; ++i){
			raw = this.getTask(i).toString(); // ex : Task[cmd=[echo, coucou, #smart], env=null, dir=null]
			raw = raw.substring(10); 				 // ex : echo, coucou, #smart], env=null, dir=null]
			raw = raw.split("]")[0]; 				 // ex : echo, coucou, #smart
			String[] tmp = raw.split(",");			 // ex : tab(echo coucou #smart)
			raw = "";
			for(int j = 0 ; j < tmp.length ; ++j)
				raw += tmp[j];
			s += this.getSchedulingPattern(i) + "\t" + raw + "\n";
		}
		return s;
	}

	
	
}
