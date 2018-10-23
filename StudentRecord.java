import java.util.HashSet;
import java.io.Serializable;

public class StudentRecord implements Serializable{
	private Course course;
	private double coursework;
	private double exam;
	private double assignment;
	private double classParticipation;
	private double overallGrade;
	
	
	public StudentRecord(Course course) {
		this.course = course;
	}
	
	public void setExam(double grade) {
		exam = grade;
	}
	
	public void setAssigment(double grade) {
		assignment = grade;
	}
	
	public void setClassParticipation(double grade) {
		classParticipation = grade;
	}
	
	
	public double getCoursework() { return coursework; }
	
	public double getExam() { return exam; }
	
	public double getAssignment() { return assignment; }
	
	public double getClassParticipation() { return classParticipation; }
	
	public double getOverallGrade() { return overallGrade; }
	
	public double updateCoursework() { 
		coursework = assignment*course.getAssignmentWeight() + classParticipation*course.getClassParticipationWeight();
		return coursework;
	}
	
	public double calOverallGrade() {
		overallGrade = exam*course.getExamWeight()+updateCoursework()*course.getCourseworkWeight();
		return overallGrade;
	}
	
	public String toString() {
		return course.getName()+"("+course.getCourseCode()+")"+":" +calOverallGrade()+"/100";
	}
	
}
