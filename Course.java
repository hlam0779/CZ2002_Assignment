import java.util.ArrayList;
import java.util.HashSet;
import java.io.Serializable;

/**
 * Represent a course in the school
 * @author Group7-SE1
 * @since 2018-11-09
 */
public class Course implements Serializable{
	private static final long serialVersionUID = -5023061144746896737L;
	private String name;						
	private String courseCode;
	private int courseStructure;					//There are three types of course structure representing by three number 1,2 and 3
	private int weightStructure;
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
	private ArrayList<CourseComponent> lecs;				//Lectures of this course
	private ArrayList<CourseComponent> tuts;				//Tutorials of this course
	private ArrayList<CourseComponent> labs;					//Lab sessions of this course
	private int totalLecVacancies;
	private int totalTutVacancies;
	private int totalLabVacancies;
	
	/**
	 * Create a course with a name, code, a professor as a coordinator and a course structure
	 * @param name	This course's name
	 * @param courseCode	This course's code
	 * @param prof	This course's coodinator
	 * @param courseStructure	This course's structure
	 */
	public Course(String name, String courseCode, Professor prof, int courseStructure) {
		this.name = name;
		this.courseCode = courseCode;
		this.coordinator = prof;
		this.courseStructure = courseStructure;
		this.lecs = new ArrayList<CourseComponent>();
		this.Students = new HashSet<Student>();
		this.tuts = null;
		this.labs = null;
		this.alreadyAddAsessmentWeight = false;
		this.alreadyAddSubComponentWeightOfCoursework = false;
		totalLecVacancies = 0;
		if (courseStructure == 2) {
			this.tuts = new ArrayList<CourseComponent>();
			totalTutVacancies = 0;
		}
		else if (courseStructure == 3){
			this.tuts = new ArrayList<CourseComponent>();
			this.labs = new ArrayList<CourseComponent>();
			totalTutVacancies = 0;
			totalLabVacancies = 0;
		}	
	}
	
	/**
	 * Get the structure of this course
	 * @return	this course's structure
	 */
	public int getCourseStructure() { return courseStructure; }
	
	/**
	 * Get the assessment weight structure of this course
	 * @return	this course's assessment weight structure
	 */
	public int getWeightStructure() { return weightStructure; }
	
	/**
	 * Get the code of this course
	 * @return this course's code
	 */
	public String getCourseCode() { return courseCode; }
	
	/**
	 * Get the name of this course
	 * @return this course's name
	 */
	public String getName() { return name; }
	
	/**
	 * Get the coordinator of this course
	 * @return this course's coordinator
	 */
	public Professor getCoordinator() { return coordinator; }
	
	/**
	 * Get the tutorials list of this course
	 * @return this course's tutorials list (using ArrayList structure to represent)
	 */
	public ArrayList<CourseComponent> getTuts() { return tuts; }
	
	/**
	 * Get the lab sessions list of this course
	 * @return this course's lab sessions list (using ArrayList structure to represent)
	 */
	public ArrayList<CourseComponent> getLabs() { return labs; }
	
	/**
	 * Get the lectures list of this course
	 * @return this course's lectures list (using ArrayList structure to represent)
	 */
	public ArrayList<CourseComponent> getLecs() { return lecs; }
	
	/**
	 * Get the students list of this course
	 * @return this course's student list (using HashSet structure to represent)
	 */
	public HashSet<Student> getStudents() { return Students; }
	
	/**
	 * Set the assessment weight structure of this course
	 * @param weightStructure this course's assessment weight structure
	 */
	public void setWeightStructure(int weightStructure) { 
		this.weightStructure = weightStructure;
	}
	
	/**
	 * create tutorials for this course
	 * @param noOfTut	Number of tutorials of this course
	 * @param capacity	This course's tutorial capacity
	 */
	public void createTut(int noOfTut, int capacity) {
		for (int i=0; i<noOfTut; i++)
			this.tuts.add(new CourseComponent(this.tuts.size(), capacity, "tutorial"));
		totalTutVacancies = noOfTut*capacity;
	}
	
	/**
	 * Create lab sessions for this course
	 * @param noOfLab	Number of lab sessions of this course
	 * @param capacity	This course's lab capacity
	 */
	public void createLab(int noOfLab, int capacity) {
		for (int i=0; i<noOfLab; i++)
			this.labs.add(new CourseComponent(this.labs.size(), capacity, "lab"));
		totalLabVacancies = noOfLab*capacity;
	}
	
	/**
	 * Create lectures for this course
	 * @param noOfLec	Number of lectures of this course
	 * @param capacity	This course's lecture capacity
	 */
	public void createLec(int noOfLec, int capacity) {
		for (int i=0; i<noOfLec; i++)
			this.lecs.add(new CourseComponent(this.lecs.size(), capacity, "lecture"));
		totalLecVacancies = noOfLec*capacity;
	}
	
	/**
	 * Reduce the total lecture vacancies by 1
	 */
	public void reduceLecVacancies() {
		totalLecVacancies--;
	}
	
	/**
	 * Reduce the total tutorial vacancies by 1
	 */
	public void reduceTutVacancies() {
		totalTutVacancies--;
	}
	
	/**
	 * Reduce the total lab vacancies by 1
	 */
	public void reduceLabVacancies() {
		totalLabVacancies--;
	}
	
	/**
	 * Return true if this course has vacancies
	 * @return boolean value indicating whether this course has vacancies
	 */
	public boolean hasVacancies() {
		if (courseStructure == 1)
			return ! (totalLecVacancies == 0);
		return ! (totalLecVacancies == 0 || totalTutVacancies == 0);
		
	}
	
	/**
	 * Get coursework weight of this course
	 * @return this course's coursework weight
	 */
	public double getCourseworkWeight() { return courseworkWeight; }
	
	/**
	 * Get exam weight of this course
	 * @return this course's exam weight
	 */
	public double getExamWeight() { return examWeight; }
	
	/**
	 * Get assignment weight of this course
	 * @return this course's assignment weight
	 */
	public double getAssignmentWeight() { return assignmentWeight; }
	
	/**
	 * Get the class participation weight of this course
	 * @return this course's class participation weight
	 */
	public double getClassParticipationWeight() { return classParticipationWeight; }
	
	/**
	 * Check if this course assessment weight has been fully entered
	 * @return boolean indicating the assessment weightage status
	 */
	public boolean getAsessmentWeightageStatus() {
		if (weightStructure == 2)
			return this.alreadyAddAsessmentWeight && this.alreadyAddSubComponentWeightOfCoursework;
		return this.alreadyAddAsessmentWeight;
	}
	
	/**
	 * Check whether the subcomponent weight of coursework has been fully entered
	 * @return boolean indicating the coursework weightage status
	 */
	public boolean getCourseworkWeightageStatus() {
		return this.alreadyAddSubComponentWeightOfCoursework;
	}
	
	/**
	 * Get the total vacancies of this course
	 * @return	this course's number of vancies
	 */
	public int getVancancies() {
		if (courseStructure >= 2)
			return Math.min(totalLecVacancies, totalTutVacancies);
		return totalLecVacancies;
	}
	
	
	
	/**
	 * Register student to a specific tutorial index of this course 
	 * Return true if successfully register, and false otherwise
	 * @param student	The student that need to be registered
	 * @param tutId		The tutorial index
	 * @return boolean value indicating the register status
	 */
	public boolean registerTut(Student student, int tutId) {
		
		//Check to ensure legal tutorial index
		if (tutId<0 || tutId>=tuts.size()) {
			System.out.println("This tutorial index does not exist");
			return false;
		}
		
		//register student to the lab and add student to student list of this course
		if (tuts.get(tutId).addStudent(student)) {
			Students.add(student);
			this.reduceTutVacancies();
			return true;
		}
		System.out.println("No available vacancies for this class index\n");
		return false;
	}
	
	/**
	 * Register student to a specific lab index of this course 
	 * Return true if successfully register, and false otherwise
	 * @param student	The student that need to be registered
	 * @param labId		The lab index
	 * @return boolean value indicating the register status
	 */
	public boolean registerLab(Student student, int labId) {
		
		//Check to ensure legal lab index
		if (labId<0 || labId>=labs.size()) {
			System.out.println("This lab session index does not exist");
			return false;
		}
		
		//register student to the lab and add student to student list of this course
		if (labs.get(labId).addStudent(student)) {
			Students.add(student);
			this.reduceLabVacancies();
			return true;
		}
		System.out.println("No available vacancies for this class index\n");
		return false;
	}
	
	
	/**
	 * Register student to a specific lecture index of this course 
	 * Return true if successfully register, and false otherwise
	 * @param student	The student that need to be registered
	 * @param lecId		The lecture index
	 * @return boolean value indicating the register status
	 */
	public boolean registerLec(Student student, int lecId) {
		
		//Check to ensure legal lecture index
		if (lecId<0 || lecId>=lecs.size()) {
			System.out.println("This lecture index does not exist");
			return false;
		}
		
		//register student to the lecture and add student to student list of this course
		if (lecs.get(lecId).addStudent(student)) {
			Students.add(student);
			System.out.println("Successfully register this student for the lecture index "+lecId+"\n");
			this.reduceLecVacancies();
			return true;
		}
		System.out.println("No available vacancies for this lecture index\n");
		return false;
	}
	
	
	/**
	 * Set coursework and exam weight and update assessment weight status 
	 * by set true the corresponding boolean variable
	 * @param weight	The value of coursework weight
	 */
	public void setCourseworkWeight(double weight) {
		this.courseworkWeight = weight;
		this.examWeight = 100.0 - weight;		//Assume that the total weight is 100 percent, then exam weight is equals to 100-courseworkWeight
		this.alreadyAddAsessmentWeight = true;
		System.out.println("The assessment weight of the exam is automatically set to "+this.examWeight+"\n");
	}
	
	
	/**
	 * set assignment weight and class participation weight and update the assessment weight of the sub-components of coursework 
	 * by set to true the corresponding boolean variable
	 * @param weight	The value of assignment weight
	 */
	public void setAssignmentWeight(double weight) {
		this.assignmentWeight = weight;
		this.classParticipationWeight = 100.0-weight;
		this.alreadyAddSubComponentWeightOfCoursework = true;
		System.out.println("The class participation weight of the coursework is automatically set to "+this.classParticipationWeight);
	}
	
	/**
	 * Get the information of this course's name and code
	 * @return message that mentions this course's name and code
	 */
	@Override
	public String toString() {
		return this.name+"("+this.courseCode+")";
	}
}
