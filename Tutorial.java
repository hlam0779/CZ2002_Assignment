import java.util.HashSet;
import java.io.Serializable;

public class Tutorial implements Serializable{
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
	
	public boolean addStudent(Student s) {
		if (capacity > currentOccupied()) {
			this.students.add(s);
			System.out.println("Successfully register this student for this tutorial");
			return true;
		}
		System.out.println("No available vacancies for this tutorial");
		return false;
	}
	
	public int currentOccupied() {
		return this.students.size();
	}
}
