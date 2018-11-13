import java.util.HashSet;
import java.io.Serializable;

/**
 * Represent a grade of a student in a specific course
 * @author Group7-SE1
 * @since 2018-11-09
 */
public class GradeRecord implements Serializable{
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
	private boolean alreadyEnterAssignmentGrade;			//These three variable indicating the grade status 
	private boolean alreadyEnterClassParticipationGrade;	//(true if grade has already been entered by some administrator and
	private boolean alreadyEnterExamGrade;				//false otherwise
	private boolean alreadyEnterCourseworkGrade; // this variable will be used if the course weightage structure is 1
	
	/**
	 * create a grade record with a course
	 * @param course	The course that this grade record object refer to
	 */
	public GradeRecord(Course course) {
		this.course = course;
		this.alreadyEnterAssignmentGrade = false;
		this.alreadyEnterClassParticipationGrade = false;
		this.alreadyEnterExamGrade = false;
		this.alreadyEnterCourseworkGrade = false;
	}
	
	/**
	 * Set the exam grade
	 * @param grade	The value of the exam grade
	 */
	public void setExam(double grade) {
		exam = grade;
		this.alreadyEnterExamGrade = true;
	}
	
	/**
	 * Set the assignment grade
	 * @param grade	The value of assignment grade
	 */
	public void setAssigment(double grade) {
		assignment = grade;
		this.alreadyEnterAssignmentGrade = true;
	}
	
	/**
	 * Set the class participation grade
	 * @param grade	The value of class participation grade
	 */
	public void setClassParticipation(double grade) {
		classParticipation = grade;
		this.alreadyEnterClassParticipationGrade = true;
	}
	
	/**
	 * Set the coursework grade
	 * @param grade	The value of the coursework grade
	 */
	public void setCoursework(double grade) {
		this.coursework = grade;
		this.alreadyEnterCourseworkGrade = true;
	}
	
	/**
	 * Get the coursework grade
	 * @return this grade record's coursework
	 */
	public double getCoursework() { return coursework; }
	
	/**
	 * Get the exam grade
	 * @return this grade record's exam
	 */
	public double getExam() { return exam; }
	
	/**
	 * Get the assignment grade
	 * @return this grade record's assignment
	 */
	public double getAssignment() { return assignment; }
	
	/**
	 * Get the class participation grade
	 * @return this grade record's class participation
	 */
	public double getClassParticipation() { return classParticipation; }
	
	/**
	 * Get the overall grade
	 * @return this grade record's overall grade
	 */
	public double getOverallGrade() { return overallGrade; }
	
	/**
	 * Get the coursework status
	 * @return coursework status
	 */
	public boolean getCourseworkStatus() { return this.alreadyEnterCourseworkGrade; }
	
	/**
	 * Get the assignment grade status
	 * @return assignment status
	 */
	public boolean getAssignmentStatus() { return this.alreadyEnterAssignmentGrade; }
	
	/**
	 * Get the class participation grade status
	 * @return class participation grade status
	 */
	public boolean getClassParticipationStatus() { return this.alreadyEnterClassParticipationGrade; }
	
	/**
	 * Get the exam status
	 * @return exam status
	 */
	public boolean getExamStatus() { return this.alreadyEnterExamGrade; }
	
	/**
	 * Calculate the coursework grade based on the assignment and class participation grade
	 * and inform error if there is some missing required information to calculate them
	 * @return boolean value indicating the update status
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
	
	
	/**
	 * Calculate the overall grade based on the coursework and exam grade
	 * and inform error if there is some missing required information to calculate them
	 * @return boolean value indicating the calculation status
	 */
	public boolean calOverallGrade() {
		
		//calculate coursework grade
		if (course.getWeightStructure() == 2 && ! updateCoursework())
			return false;
		else if ( course.getWeightStructure() == 1 && ! alreadyEnterCourseworkGrade) {
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
	
	/**
	 *This is used for printing the grade in the printTranscript method of ManageApp
	 *@return general grade information of this grade record
	 */
	@Override
	public String toString() {
		if (course.getWeightStructure() == 2) {
			if (calOverallGrade())
				return "Course: "+course+String.format("    AssignmentGrade (subWeight: %d%%): %.1f/100",(int) course.getAssignmentWeight(), assignment)+String.format("    ClassPariticipationGrade (subWeight: %d%%): %.1f/100",(int)course.getClassParticipationWeight(),classParticipation)+
						String.format("    Coursework(%d%%): %.1f/100", (int) course.getCourseworkWeight(), coursework)+String.format("    Exam(%d%%): %.1f/100",(int) course.getExamWeight(),exam)+String.format("    OverallGrade: %.1f/100", overallGrade);
			return "Course: "+course+"    status: "+statusMessage;
		}
		else if (calOverallGrade())
			return "Course: "+course+String.format("    Coursework(%d%%): %.1f/100",(int) course.getCourseworkWeight(), coursework)+String.format("    Exam(%d%%): %.1f/100",(int) course.getExamWeight(),exam)+String.format("    OverallGrade: %.1f/100", overallGrade);
		return "Course: "+course+"    status: "+statusMessage;
	}
}
