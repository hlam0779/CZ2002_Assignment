import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;

public class Student implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1110733458217604607L;
	private String name;								
	private String matricNo;							
	private int yearOfBirth;
	private String major;
	private int yearOfAdmission;
	private HashMap<String,GradeRecord>  transcript;		//Map a course code to a grade record
	public static int noOfStudents;
	
	
	public Student(String name, String matricNo, int yearOfBirth, int yearOfAdmission, String major) {
		this.name = name;
		this.matricNo = matricNo;
		this.yearOfBirth = yearOfBirth;
		this.major = major;
		this.yearOfAdmission = yearOfAdmission;
		this.transcript = new HashMap<String,GradeRecord>();
		noOfStudents++;
	}
	
	public String getName() { return name;  }
	
	public String getMatric() { return matricNo; }
	
	public String getMajor() { return major; }
	
	public int yearOfBirth() { return yearOfBirth; }
	
	public int getYearOfAdmission() { return yearOfAdmission; }
	
	public HashMap<String,GradeRecord> getTranscript() { return transcript; }
	
	public boolean registerTut(String courseCode, int index) {
		Course course = ManageApp.courseList.get(courseCode);
		if (course.registerTut(this, index)) {
			if (! transcript.containsKey(courseCode))
				transcript.put(courseCode, new GradeRecord(course));
			return true;
		}
		return false;
	}
	
	public boolean registerLab(String courseCode, int index) {
		Course course = ManageApp.courseList.get(courseCode);
		if (course.registerLab(this, index)) {
			if (! transcript.containsKey(courseCode))
				transcript.put(courseCode, new GradeRecord(course));
			return true;
		}
		return false;
	}
	
	public boolean registerLec(String courseCode, int index) {
		Course course = ManageApp.courseList.get(courseCode);
		if (course.registerLec(this, index)) {
			if (! transcript.containsKey(courseCode))
				transcript.put(courseCode, new GradeRecord(course));
			return true;
		}
		return false;
	}
	
	//Print student information, this is used for printing student list by lectures, tutorials and lab sessions in the printStudentList method of ManageApp
	@Override
	public String toString() {
		return "Name: "+name+"    MatricNumber:  "+matricNo+ "    Major: "+major;
	}

}
