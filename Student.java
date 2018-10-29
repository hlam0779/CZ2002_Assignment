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
	private HashMap<Course,GradeRecord>  transcript;
	public static int noOfStudents;
	
	
	public Student(String name, String matricNo, int yearOfBirth, int yearOfAdmission, String major) {
		this.name = name;
		this.matricNo = matricNo;
		this.yearOfBirth = yearOfBirth;
		this.major = major;
		this.yearOfAdmission = yearOfAdmission;
		this.transcript = new HashMap<Course,GradeRecord>();
		noOfStudents++;
	}
	
	public String getName() { return name;  }
	
	public String getMatric() { return matricNo; }
	
	public String getMajor() { return major; }
	
	public int yearOfBirth() { return yearOfBirth; }
	
	public int getYearOfAdmission() { return yearOfAdmission; }
	
	public HashMap<Course,GradeRecord> getTranscript() { return transcript; }
	
	public boolean registerTut(Course course, int index) {
		if (course.registerTut(this, index)) {
			if (! transcript.containsKey(course))
				transcript.put(course, new GradeRecord(course));
			return true;
		}
		return false;
	}
	
	public boolean registerLab(Course course, int index) {
		if (course.registerLab(this, index)) {
			if (! transcript.containsKey(course))
				transcript.put(course, new GradeRecord(course));
			return true;
		}
		return false;
	}
	
	public boolean registerLec(Course course, int index) {
		if (course.registerLec(this, index)) {
			if (! transcript.containsKey(course))
				transcript.put(course, new GradeRecord(course));
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
	
	//Print student information, this is used for printing student list by lectures, tutorials and lab sessions in the printStudentList method of ManageApp
	@Override
	public String toString() {
		return "Name: "+name+"    MatricNumber:  "+matricNo+ "    Major: "+major;
	}
	
	//Two students are the same if they have the same matric number
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Student) )
			return false;
		Student s = (Student) o;
		return (this.matricNo.equals(s.getMatric()));
	}
	
	//This is used to hash students into HashMap or HashSet object using matric number
	@Override
	public int hashCode() {
		if (this.matricNo == null)
			return super.hashCode();
		return this.matricNo.hashCode();
	}
}
