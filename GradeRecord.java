import java.util.HashSet;
import java.io.Serializable;

public class GradeRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3502479376698527151L;
	private Course course;
	private double coursework;						/*Coursework grade for the corresponding course, this is calculated based on 
													 *the assignment and class participation grade 
													 */
	private double exam;							//Exam grade for the corresponding course (max 100)
	private double assignment;						//Assignment grade for the corresponding course (max 100)
	private double classParticipation;				//Class participation grade for the corresponding course (max 100)
	private double overallGrade;					//Overall grade, this is calculated based on the coursework and exam grade (max 100)
	
	private String message;							/*This will be printed to inform the error when trying to 
													 *get access to the grade that has not been successfully calculated
													 */
	private boolean alreadyEnterAssignmentGrade;			//These three variable indicating the grade status 
	private boolean alreadyEnterClassParticipationGrade;	//(true if grade has already been entered by some administrator and
	private boolean alreadyEnterExamGrade;					//false otherwise
	
	
	public GradeRecord(Course course) {
		this.course = course;
		this.alreadyEnterAssignmentGrade = false;
		this.alreadyEnterClassParticipationGrade = false;
		this.alreadyEnterExamGrade = false;
	}
	
	public void setExam(double grade) {
		exam = grade;
		this.alreadyEnterExamGrade = true;
	}
	
	public void setAssigment(double grade) {
		assignment = grade;
		this.alreadyEnterAssignmentGrade = true;
	}
	
	public void setClassParticipation(double grade) {
		classParticipation = grade;
		this.alreadyEnterClassParticipationGrade = true;
	}
	
	
	public double getCoursework() { return coursework; }
	
	public double getExam() { return exam; }
	
	public double getAssignment() { return assignment; }
	
	public double getClassParticipation() { return classParticipation; }
	
	public double getOverallGrade() { return overallGrade; }
	
	
	/*
	 * Calculate the coursework grade based on the assignment and class participation grade
	 * and inform error if there is some missing required information to calculate them
	 */
	public boolean updateCoursework() { 
		
		//Check the coursework weight status
		if (! course.getCourseworkWeightageStatus()) {
			message = "Cannot compute coursework grade, please update the sub-component weight of coursework first";
			return false;
		}
		//Check the grade status of assignment and class participation
		if (!(this.alreadyEnterAssignmentGrade && this.alreadyEnterClassParticipationGrade)) {
			message = "Cannot compute coursework grade, please enter this student's grade for each component of coursework first";
			return false;
		}
		//Calculate the course if there is no missing required information
		coursework = (assignment/100)*course.getAssignmentWeight() + (classParticipation/100)*course.getClassParticipationWeight();
		return true;
	}
	
	
	/*
	 * Calculate the overall grade based on the coursework and exam grade
	 * and inform error if there is some missing required information to calculate them
	 */
	public boolean calOverallGrade() {
		
		//calculate coursework grade
		if (! updateCoursework())
			return false;
		
		//Check the assessment weight status of coursework and exam
		if (! course.getAsessmentWeightageStatus()) {
			message = "Cannot compute overall grade, please update the coursework weight and exam weight first";
			return false;
		}
		
		//Check the grade status of the exam
		if (! this.alreadyEnterExamGrade) {
			message = "Cannot compute overall grade, please enter the exam grade of this student first";
			return false;
		}
		
		//Calculate the overall grade if there is no missing required information
		overallGrade = (exam/100)*course.getExamWeight()+(coursework/100)*course.getCourseworkWeight();
		return true;
	}
	
	/*
	 *This is used for printing the grade in the printTranscript method of ManageApp  
	 */
	@Override
	public String toString() {
		if (calOverallGrade())
			return "Course: "+course+"    AssignmentGrade: "+assignment+"/100"+"    ClassPariticipationGrade: "+classParticipation+"/100"+"    Exam: "+"/100"+"    OverallGrade:" +overallGrade+"/100";
		return "Course: "+course+"    status: "+message;
	}
}
