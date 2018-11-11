
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * This class contains static method to manipulate the data
 * @author Group7-SE1
 * @since 2018-11-09
 */
public class SerializeDB
{
	/**
	 * read List object from a binary file
	 * given the file name
	 * @param filename The file name that need to be read
	 * @return List object
	 */
	public static List readSerializedListObject(String filename) {
		List pDetails = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			pDetails = (ArrayList) in.readObject();
			in.close();
			fis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return pDetails;
	}
	
	/**
	 * read Data object from a binary file and create one if there is no such file
	 * (Data class is created in this assignment)
	 * @param filename The file name that need to be read
	 * @return Data object
	 */
	public static Data readSerializedDataObject(String filename) {
		Data data = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			data = (Data) in.readObject();
			in.close();
			fis.close();
		} catch (IOException ex) {
			return new Data();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return data;
	}
	
	/**
	 * Store List Object in a binary file 
	 * (this function is not used in this assignment because the data is stored as a Data object)
	 * @param filename	The file name that need to be written into
	 * @param list		The list object that need to be written into file
	 */
	public static void writeSerializedListObject(String filename, List list) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(list);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Store Data object in a binary file
	 * @param filename	The file name that need to be written into
	 * @param data		The Data object that need to be written into file
	 */
	public static void writeSerializedDataObject(String filename, Data data) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(data);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
