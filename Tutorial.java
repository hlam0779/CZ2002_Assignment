import java.util.HashSet;
import java.io.Serializable;

public class Tutorial implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6595084104792911570L;
	private int tutId;
	private int capacity;
	private HashSet<Student> students;
	
	public Tutorial(int tutId, int capacity) {
		this.tutId = tutId;
		this.students = new HashSet<Student>();
		this.capacity = capacity;
	}
	
	public int getTutId() { return tutId; }
	
	public int getCapacity() { return capacity; }
	
	public int getVacancies() { return capacity-currentOccupied(); }
	
	public HashSet<Student> getStudents() { return students; }
	
	public int currentOccupied() { return this.students.size(); }
	
	/*Add a student to the student list of this tutorial 
	 * Return true if successfully add the student to this course and false otherwise
	 */
	public boolean addStudent(Student s) {
		
		//Check vacancies
		if (capacity > currentOccupied()) {
			this.students.add(s);
			System.out.println("Successfully register this student for this tutorial\n");
			return true;
		}
		System.out.println("No available vacancies for this tutorial\n");
		return false;
	}
	
	//This is used for printing the vacancies of this tutorial in the checkVacancies method of the ManageApp
	@Override
	public String toString() {
		return "This tutorial has "+ (capacity-currentOccupied())+ " vacancies\n";
	}
}
