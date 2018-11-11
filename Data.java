import java.util.HashMap;
import java.io.Serializable;

/**
 * Represent an object that contains all data in this school
 * @author Group7-SE1
 * @since 2018-11-09
 */
public class Data implements Serializable {
	
	//private static final long serialVersionUID = -6530338319669736230L;
	private HashMap<String,Student> studentList;
	private HashMap<String,Course> courseList;
	
	/**
	 * Create a Data object
	 * Initialize student list and course list
	 */
	public Data() {
		studentList = new HashMap<String,Student>();
		courseList = new HashMap<String,Course>();
	}
	
	/**
	 * Get the data of students
	 * @return	student data (using hashMap structure to represent)
	 */
	public HashMap<String,Student> getStudentList(){
		return studentList;
	}
	
	/**
	 * Get the data of courses
	 * @return course data (using hashMap structure to represent)
	 */
	public HashMap<String,Course> getCourseList(){
		return courseList;
	}
}
