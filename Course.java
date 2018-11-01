import java.util.ArrayList;
import java.util.HashSet;
import java.io.Serializable;


public class Course implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5023061144746896737L;
	private String name;						
	private String courseCode;
	private int courseStructure;					//There are three types of course structure representing by three number 1,2 and 3
	private Professor coordinator;					//Coordinator of this course
	private double courseworkWeight;				//Asessment weight of coursework for this course (unit: percent(%))
	private double examWeight;						//Asessment weight of exam for this course (unit: percent(%))
	private double assignmentWeight;				//Asessment weight of assignment for coursework of this course (unit: percent(%))
	private double classParticipationWeight;		//Asessment weight of class participation coursework of this course (unit: percent(%))
	
	private boolean alreadyAddAsessmentWeight;		/* indicating the status of assessment weight of coursework and exam (true if assessment weight of 
													coursework has been entered by the administrator and false otherwise*/
	
	private boolean alreadyAddSubComponentWeightOfCoursework; /*indicating the status of assessment weight of sub-components of coursework
	 														    (true if the assessment weight of assignment and class participation has been entered
	 														    by the administrator and false otherwise*/
	private HashSet<Student> Students;				//Students who has registered this course
	private ArrayList<Lecture> lecs;				//Lectures of this course
	private ArrayList<Tutorial> tuts;				//Tutorials of this course
	private ArrayList<Lab> labs;					//Lab sessions of this course
	private int totalLecVacancies;
	private int totalTutVacancies;
	private int totalLabVacancies;
	
	public Course(String name, String courseCode, Professor prof, int courseStructure) {
		this.name = name;
		this.courseCode = courseCode;
		this.coordinator = prof;
		this.courseStructure = courseStructure;
		this.lecs = new ArrayList<Lecture>();
		this.Students = new HashSet<Student>();
		this.tuts = null;
		this.labs = null;
		this.alreadyAddAsessmentWeight = false;
		this.alreadyAddSubComponentWeightOfCoursework = false;
		totalLecVacancies = 0;
		if (courseStructure == 2) {
			this.tuts = new ArrayList<Tutorial>();
			totalTutVacancies = 0;
		}
		else if (courseStructure == 3){
			this.tuts = new ArrayList<Tutorial>();
			this.labs = new ArrayList<Lab>();
			totalTutVacancies = 0;
			totalLabVacancies = 0;
		}	
	}
	
	public int getCourseStructure() { return courseStructure; }
	
	public String getCourseCode() { return courseCode; }
	
	public String getName() { return name; }
	
	public Professor getCoordinator() { return coordinator; }
	
	public ArrayList<Tutorial> getTuts() { return tuts; }
	
	public ArrayList<Lab> getLabs() { return labs; }
	
	public ArrayList<Lecture> getLecs() { return lecs; }
	
	public HashSet<Student> getStudents() { return Students; }
	
	
	
	public void createTut(int noOfTut, int capacity) {
		for (int i=0; i<noOfTut; i++)
			this.tuts.add(new Tutorial(this, this.tuts.size(), capacity));
		totalTutVacancies = noOfTut*capacity;
	}
	
	public void createLab(int noOfLab, int capacity) {
		for (int i=0; i<noOfLab; i++)
			this.labs.add(new Lab(this, this.labs.size(), capacity));
		totalLabVacancies = noOfLab*capacity;
	}
	
	public void createLec(int noOfLec, int capacity) {
		for (int i=0; i<noOfLec; i++)
			this.lecs.add(new Lecture(this, this.lecs.size(), capacity));
		totalLecVacancies = noOfLec*capacity;
	}
	
	public void reduceLecVacancies() {
		totalLecVacancies--;
	}
	
	public void reduceTutVacancies() {
		totalTutVacancies--;
	}
	
	public void reduceLabVacancies() {
		totalLabVacancies--;
	}
	
	public boolean hasVacancies() {
		if (courseStructure == 1)
			return ! (totalLecVacancies == 0);
		if (courseStructure == 2)
			return ! (totalLecVacancies == 0 || totalTutVacancies == 0);
		return ! (totalLecVacancies == 0 || totalTutVacancies == 0 || totalLabVacancies == 0);
	}

	public double getCourseworkWeight() { return courseworkWeight; }
	
	public double getExamWeight() { return examWeight; }
	
	public double getAssignmentWeight() { return assignmentWeight; }
	
	public double getClassParticipationWeight() { return classParticipationWeight; }
	
	public boolean getAsessmentWeightageStatus() {
		return this.alreadyAddAsessmentWeight && this.alreadyAddSubComponentWeightOfCoursework;
	}
	
	public boolean getCourseworkWeightageStatus() {
		return this.alreadyAddSubComponentWeightOfCoursework;
	}
	
	public int getVancancies() {
		if (courseStructure == 3)
			return Math.min(totalLecVacancies, Math.min(totalTutVacancies, totalLabVacancies));
		else if (courseStructure == 2)
			return Math.min(totalLecVacancies, totalTutVacancies);
		else
			return totalLecVacancies;
	}
	
	/* Register student to a specific tutorial index of this course 
	 * Return true if successfully register, and false otherwise
	 */
	public boolean registerTut(Student s, int tutId) {
		
		//Check to ensure legal tutorial index
		if (tutId<0 || tutId>=tuts.size()) {
			System.out.println("This tutorial index does not exist");
			return false;
		}
		
		//register student to the lab and add student to student list of this course
		if (tuts.get(tutId).addStudent(s)) {
			Students.add(s);
			return true;
		}
		return false;
	}
	
	/* Register student to a specific lab index of this course 
	 * Return true if successfully register, and false otherwise
	 */
	public boolean registerLab(Student s, int labId) {
		
		//Check to ensure legal lab index
		if (labId<0 || labId>=labs.size()) {
			System.out.println("This lab session index does not exist");
			return false;
		}
		
		//register student to the lab and add student to student list of this course
		if (labs.get(labId).addStudent(s)) {
			Students.add(s);
			return true;
		}
		return false;
	}
	
	
	/* Register student to a specific lecture index of this course 
	 * Return true if successfully register, and false otherwise
	 */
	public boolean registerLec(Student s, int lecId) {
		
		//Check to ensure legal lecture index
		if (lecId<0 || lecId>=lecs.size()) {
			System.out.println("This lecture index does not exist");
			return false;
		}
		
		//register student to the lecture and add student to student list of this course
		if (lecs.get(lecId).addStudent(s)) {
			Students.add(s);
			return true;
		}
		return false;
	}
	
	
	/*
	 * Set coursework and exam weight and update assessment weight status 
	 * by set true the corresponding boolean variable
	 */
	public void setCourseworkWeight(double weight) {
		this.courseworkWeight = weight;
		this.examWeight = 100.0 - weight;		//Assume that the total weight is 100 percent, then exam weight is equals to 100-courseworkWeight
		this.alreadyAddAsessmentWeight = true;
		System.out.println("The assessment weight of the exam is automatically set to "+this.examWeight+"\n");
	}
	
	
	/*set assignment weight and class participation weight and update the assessment weight of the sub-components of coursework 
	 * by set to true the corresponding boolean variable
	 */
	public void setAssignmentWeight(double weight) {
		this.assignmentWeight = weight;
		this.classParticipationWeight = 100.0-weight;
		this.alreadyAddSubComponentWeightOfCoursework = true;
		System.out.println("The class participation weight of the coursework is automatically set to "+this.classParticipationWeight);
	}
	
	//Two courses are the same if they the same course code 
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof Course))
			return false;
		Course c = (Course) o;
		if (this.courseCode.equals(c.getCourseCode()))
			return true;
		return false;
	}
	
	//This is used to hash the course to the HashMap or HashSet Object
	@Override
	public int hashCode() {
		return this.courseCode.hashCode();
	}
	
	@Override
	public String toString() {
		return this.name+"("+this.courseCode+")";
	}
}
