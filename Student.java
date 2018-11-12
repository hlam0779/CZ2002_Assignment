import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Represent student enrolled in the school
 * @author Group7-SE1
 * @since 2018-09-11
 */
public class Student implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1110733458217604607L;
	private String name;								
	private String matricNo;							
	private String major;
	private HashMap<String,GradeRecord>  transcript;		//Map a course code to a grade record
	public static int noOfStudents;
	
	
	/**
	 * Create a student object with the name, matric number,
	 * year of birth, year of admission and major.
	 * The name should include both first and last name
	 * @param name	Student's name
	 * @param matricNo	Student's matric number
	 * @param yearOfBirth	Student's year of birth
	 * @param yearOfAdmission	Student's year of admission
	 * @param major	  Student's major
	 */
	public Student(String name, String matricNo, String major) {
		this.name = name;
		this.matricNo = matricNo;
		this.major = major;
		this.transcript = new HashMap<String,GradeRecord>();
		noOfStudents++;
	}
	
	/**
	 * Get the name of this student
	 * @return this student's name
	 */
	public String getName() { return name;  }
	
	/**
	 * Get the matric number of this student
	 * @return this student's matric number
	 */
	public String getMatric() { return matricNo; }
	
	/**
	 * Get the major of this student
	 * @return this student's major
	 */
	public String getMajor() { return major; }
	
	/**
	 * Get the transcript of this student
	 * @return this student's transcript (using hashMap structure to represent)
	 */
	public HashMap<String,GradeRecord> getTranscript() { return transcript; }
	
	/**
	 * Register student for a tutorial class of a course using course code and the corresponding tutorial index
	 * Return true if successfully register, false otherwise
	 * @param courseCode	Course's code
	 * @param index 	Tutorial's index
	 * @return	boolean value indicating the register status
	 */
	public boolean registerTut(String courseCode, int index) {
		Course course = ManageApp.courseList.get(courseCode);
		if (course.registerTut(this, index)) {
			if (! transcript.containsKey(courseCode))
				transcript.put(courseCode, new GradeRecord(course));
			return true;
		}
		return false;
	}
	
	/**
	 * Register student for a lab class of a course using course code and the corresponding lab index
	 * Return true if successfully register, false otherwise
	 * @param courseCode	Course's code
	 * @param index 	Lab's index
	 * @return	boolean value indicating the register status
	 */
	public boolean registerLab(String courseCode, int index) {
		Course course = ManageApp.courseList.get(courseCode);
		if (course.registerLab(this, index)) {
			if (! transcript.containsKey(courseCode))
				transcript.put(courseCode, new GradeRecord(course));
			return true;
		}
		return false;
	}
	
	/**
	 * Register student for a lecture of a course using course code and the corresponding lecture index
	 * Return true if successfully register, false otherwise
	 * @param courseCode	Course's code
	 * @param index 	Tutorial's index
	 * @return	boolean value indicating the register status
	 */
	public boolean registerLec(String courseCode, int index) {
		Course course = ManageApp.courseList.get(courseCode);
		if (course.registerLec(this, index)) {
			if (! transcript.containsKey(courseCode))
				transcript.put(courseCode, new GradeRecord(course));
			return true;
		}
		return false;
	}
	
	/**
	 * Get student information, this is used for printing student list by lectures, 
	 * tutorials and lab sessions in the printStudentList method of ManageApp
	 * @return student's general information
	 */
	@Override
	public String toString() {
		return "Name: "+name+"    MatricNumber: "+matricNo+ "    Major: "+major;
	}
}
