import java.util.HashMap;
import java.io.Serializable;

public class Data implements Serializable {
	/**
	 * 
	 */
	//private static final long serialVersionUID = -6530338319669736230L;
	private HashMap<String,Student> studentList;
	private HashMap<String,Course> courseList;

	public Data() {
		studentList = new HashMap<String,Student>();
		courseList = new HashMap<String,Course>();
	}
	
	public HashMap<String,Student> getStudentList(){
		return studentList;
	}
	
	public HashMap<String,Course> getCourseList(){
		return courseList;
	}

}
