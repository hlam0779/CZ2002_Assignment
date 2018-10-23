import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;

public class Student implements Serializable{
	private String name;
	private String NRIC;
	private int matricNo;
	private int yearOfBirth;
	private String major;
	private int yearOfAdmission;
	private HashMap<Course,StudentRecord>  transcript;
	public static int noOfStudents;
	
	
	public Student(String name, String NRIC, int yearOfBirth, int yearOfAdmission, String major) {
		this.name = name;
		this.NRIC = NRIC;
		this.yearOfBirth = yearOfBirth;
		this.major = major;
		this.yearOfAdmission = yearOfAdmission;
		this.transcript = new HashMap<Course,StudentRecord>();
		noOfStudents++;
		this.matricNo = noOfStudents;
	}
	
	public String getName() { return name;  }
	
	public String getNRIC() { return NRIC; }
	
	public String getMajor() { return major; }
	
	public int yearOfBirth() { return yearOfBirth; }
	
	public int getYearOfAdmission() { return yearOfAdmission; }
	
	public boolean registerTut(Course course, int index) {
		if (course.registerTut(this, index)) {
			if (! transcript.containsKey(course))
				transcript.put(course, new StudentRecord(course));
			return true;
		}
		return false;
	}
	
	public boolean registerLab(Course course, int index) {
		if (course.registerLab(this, index)) {
			if (! transcript.containsKey(course))
				transcript.put(course, new StudentRecord(course));
			return true;
		}
		return false;
	}
	
	public boolean registerLec(Course course, int index) {
		if (course.registerLec(this, index)) {
			if (! transcript.containsKey(course))
				transcript.put(course, new StudentRecord(course));
			return true;
		}
		return false;
	}
	
	public void enterGrade(Course course, int i, double grade) {
		if (i == 1)
			transcript.get(course).setClassParticipation(grade);
		else if (i == 2)
			transcript.get(course).setAssigment(grade);
		else
			transcript.get(course).setExam(grade);
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Student) )
			return false;
		Student s = (Student) o;
		return (this.NRIC.equals(s.getNRIC()));
	}
	
	public int hashCode() {
		return NRIC.hashCode();
	}
}
