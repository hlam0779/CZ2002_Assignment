import java.util.ArrayList;
import java.util.HashSet;
import java.io.Serializable;

public class Course implements Serializable{
	private String name;
	private String courseCode;
	private int courseStructure;
	private HashSet<Student> tutStudents;
	private HashSet<Student> labStudents;
	private HashSet<Student> lecStudents;
	private ArrayList<Lecture> lecs;
	private ArrayList<Tutorial> tuts;
	private ArrayList<Lab> labs;
	private Professor cordinator;
	private double courseworkWeight;
	private double examWeight;
	private double assignmentWeight;
	private double classParticipationWeight;
	
	
	public Course(String name, String courseCode, Professor prof, int courseStructure) {
		this.name = name;
		this.courseCode = courseCode;
		this.cordinator = prof;
		this.courseStructure = courseStructure;
		this.lecs = new ArrayList<Lecture>();
		this.lecStudents = new HashSet<Student>();
		this.tuts = null;
		this.labs = null;
		this.labStudents = null;
		this.tutStudents = null;
		if (courseStructure == 2){
			this.tuts = new ArrayList<Tutorial>();
			this.tutStudents = new HashSet<Student>();
		}
		else if (courseStructure == 1){
			this.tuts = new ArrayList<Tutorial>();
			this.labs = new ArrayList<Lab>();
			this.tutStudents = new HashSet<Student>();
			this.labStudents = new HashSet<Student>();
		}	
	}
	
	public String getCourseCode() { return courseCode; }
	
	public String getName() { return name; }
	
	public Professor getCordinator() { return cordinator; }
	
	public ArrayList<Tutorial> getTuts() { return tuts; }
	
	public ArrayList<Lab> getLabs() { return labs; }
	
	public ArrayList<Lecture> getLecs() { return lecs; }
	
	public HashSet<Student> getTutStudents() { return tutStudents; }
	
	public HashSet<Student> getLabStudents() { return labStudents; }
	
	public HashSet<Student> getLecStudents() { return lecStudents; }
	
	public void createTut(int noOfTut, int capacity) {
		for (int i=0; i<noOfTut; i++)
			this.tuts.add(new Tutorial(this.tuts.size(), capacity));
	}
	
	public void createLab(int noOfLab, int capacity) {
		for (int i=0; i<noOfLab; i++)
			this.labs.add(new Lab(this.labs.size(), capacity));
	}
	
	public void createLec(int noOfLec, int capacity) {
		for (int i=0; i<noOfLec; i++)
			this.lecs.add(new Lecture(this.lecs.size(), capacity));
	}
	
	public boolean registerTut(Student s, int tutId) {
		if (tutStudents.contains(s)) {
			System.out.println("This student has already successfully registered for certain tutorial of this course before");
			return false;
		}
		if (tutId<0 || tutId>=tuts.size()) {
			System.out.println("This tutorial index does not exist");
			return false;
		}
		if (tuts.get(tutId).addStudent(s)) {
			tutStudents.add(s);
			return true;
		}
		return false;
	}
	
	public boolean registerLab(Student s, int labId) {
		if (labStudents.contains(s)) {
			System.out.println("This student has already successfully registered for certain lab session of this course before");
			return false;
		}
		if (labId<0 || labId>=labs.size()) {
			System.out.println("This lab session index does not exist");
			return false;
		}
		if (labs.get(labId).addStudent(s)) {
			labStudents.add(s);
			return true;
		}
		return false;
	}
	
	public boolean registerLec(Student s, int lecId) {
		if (lecStudents.contains(s)) {
			System.out.println("This student has already successfully registered for certain lab session of this course before");
			return false;
		}
		if (lecId<0 || lecId>=lecs.size()) {
			System.out.println("This lecture index does not exist");
			return false;
		}
		if (lecs.get(lecId).addStudent(s)) {
			lecStudents.add(s);
			return true;
		}
		return false;
	}
	
	public void setCourseworkWeighth(double weight) {
		this.courseworkWeight = weight;
		this.examWeight = 100.0 - weight;
	}
	
	public void setAssignmentWeight(double weight) {
		this.assignmentWeight = weight;
		this.classParticipationWeight = weight;
	}
	
	public double getCourseworkWeight() { return courseworkWeight; }
	
	public double getExamWeight() { return examWeight; }
	
	public double getAssignmentWeight() { return assignmentWeight; }
	
	public double getClassParticipationWeight() { return classParticipationWeight; }
	
	
	public boolean equals(Object o) {
		Course c = (Course) o;
		if (this.name.equals(c.getName()))
			return true;
		if (this.courseCode.equals(c.getCourseCode()))
			return true;
		return false;
	}
	
	public int hashCode() {
		return this.courseCode.hashCode();
	}
	
}
