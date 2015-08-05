/**
 * 
 */
package fr.utbm.to52.smarthome.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Basic static function for IO
 * @author Alexandre Guyon
 *
 */
public class BasicIO {

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
	 * Write content to a file
	 * @param path The path to the file
	 * @param content The content to write
	 */
	@SuppressWarnings("resource")
	public static void write(String path, String content){ 
		try {
			
			FileWriter fw = new FileWriter(path);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
