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
	
	private String statusMessage;							/*This will be printed to inform the error when trying to 
													 *get access to the grade that has not been successfully calculated
													 */
	public boolean alreadyEnterAssignmentGrade;			//These three variable indicating the grade status 
	public boolean alreadyEnterClassParticipationGrade;	//(true if grade has already been entered by some administrator and
	public boolean alreadyEnterExamGrade;				//false otherwise, these variables are set to public for convenience
	public boolean alreadyEnterCourseworkGrade; // this variable will be used if the course weightage structure is 1
	
	
	public GradeRecord(Course course) {
		this.course = course;
		this.alreadyEnterAssignmentGrade = false;
		this.alreadyEnterClassParticipationGrade = false;
		this.alreadyEnterExamGrade = false;
		this.alreadyEnterCourseworkGrade = false;
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
	
	public void setCoursework(double grade) {
		this.coursework = grade;
		this.alreadyEnterCourseworkGrade = true;
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
			statusMessage = "Cannot compute coursework grade, please update the sub-component weight of coursework first";
			return false;
		}
		//Check the grade status of assignment and class participation
		if (!(this.alreadyEnterAssignmentGrade && this.alreadyEnterClassParticipationGrade)) {
			statusMessage = "Cannot compute coursework grade, please enter this student's grade for each component of coursework first";
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
		if (course.getWeightStructure() == 2 && ! updateCoursework())
			return false;
		else if ( ! alreadyEnterCourseworkGrade) {
			statusMessage = "Cannot compute overall grade, please enter the coursework grade first";
			return false;
		}
		
		//Check the assessment weight status of coursework and exam
		if (! course.getAsessmentWeightageStatus()) {
			statusMessage = "Cannot compute overall grade, please update the coursework weight and exam weight first";
			return false;
		}
		
		//Check the grade status of the exam
		if (! this.alreadyEnterExamGrade) {
			statusMessage = "Cannot compute overall grade, please enter the exam grade of this student first";
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
		if (course.getWeightStructure() == 2) {
			if (calOverallGrade())
				return "Course: "+course+String.format("    AssignmentGrade (subWeight: %d%%): %.1f/100",(int) course.getAssignmentWeight(), assignment)+String.format("    ClassPariticipationGrade (subWeight: %d%%): %.1f/100",(int)course.getClassParticipationWeight(),classParticipation)+
						String.format("    Coursework(%d%%): %.1f/100", course.getCourseworkWeight(), coursework)+String.format("    Exam(%d%%): %.1f/100",(int) course.getExamWeight(),exam)+String.format("    OverallGrade: %.1f/100", overallGrade);
			return "Course: "+course+"    status: "+statusMessage;
		}
		else if (calOverallGrade())
			return "Course: "+course+String.format("    Coursework(%d%%): %.1f/100",(int) course.getCourseworkWeight(), coursework)+String.format("    Exam(%d%%): %.1f/100",(int) course.getExamWeight(),exam)+String.format("    OverallGrade: %.1f/100", overallGrade);
		return "Course: "+course+"    status: "+statusMessage;
	}
	
}
