import java.util.HashSet;
import java.io.Serializable;

public class Lab implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9019537250606693375L;
	private Course course;
	private int labId;
	private HashSet<Student> students;
	private int capacity;
	
	public Lab(Course course, int labId, int capacity) {
		this.course = course;
		this.labId = labId;
		this.capacity = capacity;
		this.students = new HashSet<Student>();
	}
	
	public int getLabId() { return labId; }
	
	public int getCapacity() { return capacity; }
	
	public int getVacancies() { return capacity-currentOccupied(); }
	
	public HashSet<Student> getStudents() { return students; }
	
	public int currentOccupied() { return this.students.size(); }
	
	/*Add a student to the student list of this lab session 
	 * Return true if successfully add the student to this course and false otherwise
	 */
	public boolean addStudent(Student s) {
		
		//Check vacancies
		if (capacity > currentOccupied()) {
			this.students.add(s);
			course.reduceLabVacancies();
			System.out.println("Successfully register this student for this lab session\n");
			return true;
		}
		System.out.println("No available vacancies for this lab session\n");
		return false;
	}
	
	//This is used for printing the vacancies of this lab session in the checkVacancies method of the ManageApp
	@Override
	public String toString() {
		return "This lab has "+ (capacity-currentOccupied())+ " vacancies\n";
	}
}
