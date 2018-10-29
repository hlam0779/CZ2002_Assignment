import java.util.HashSet;
import java.io.Serializable;

public class Lecture implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5281074533738230052L;
	private int lecId;
	private int capacity;
	private HashSet<Student> students;
	
	public Lecture(int lecId, int capacity) {
		this.lecId = lecId;
		this.students = new HashSet<Student>();
		this.capacity = capacity;
	}
	
	public int getLecId() { return lecId; }
	
	public int getCapacity() { return capacity; }
	
	public int getVacancies() { return capacity-currentOccupied(); }
	
	public HashSet<Student> getStudents() { return students; }
	
	public int currentOccupied() { return this.students.size(); }
	
	/*Add a student to the student list of this lecture
	 * Return true if successfully add the student to this course and false otherwise
	 */
	public boolean addStudent(Student s) {
		
		//Check vacancies
		if (capacity > currentOccupied()) {
			this.students.add(s);
			System.out.println("Successfully register this student for this lecture\n");
			return true;
		}
		System.out.println("No available vacancies for this lecture\n");
		return false;
	}
	
	//This is used for printing the vacancies of this lecture in the checkVacancies method of the ManageApp
	@Override
	public String toString() {
		return "This lecture has "+ (capacity-currentOccupied())+ " vacancies\n";
	}
}
