/**
 * 
 */
package aarora.AutoCompleteSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Utility functions
 * @author ashimaarora
 *
 */
public class UtilityFunctions {
	public UtilityFunctions() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Loads an object from a file
	 * @param filePath
	 */
	public static Object loadFromFile(String filePath) {
		FileInputStream fi;
		try {
			fi = new FileInputStream(new File(filePath));
			ObjectInputStream oi = new ObjectInputStream(fi);
			Object o = oi.readObject();
			oi.close();
			fi.close();
			return o;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Writes an object to a file location
	 * @param filePath
	 * @param o
	 */
	public static void writeToFile(String filePath, Object o) {
		FileOutputStream f;
		try {
			f = new FileOutputStream(new File(filePath));
			ObjectOutputStream out = new ObjectOutputStream(f);
			
			// Write objects to file
			out.writeObject(o);
			out.close();
			f.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
