import java.util.HashSet;
import java.io.Serializable;

public class Lab implements Serializable{
	private int labId;
	private HashSet<Student> students;
	private int capacity;
	
	public Lab(int labId, int capacity) {
		this.labId = labId;
		this.capacity = capacity;
		this.students = new HashSet<Student>();
	}
	
	public int getLabId() { return labId; }
	
	public int getCapacity() { return capacity; }
	
	public int getVacancies() { return capacity-currentOccupied(); }
	
	public HashSet<Student> getStudents() { return students; }
	
	public boolean addStudent(Student s) {
		if (capacity > currentOccupied()) {
			this.students.add(s);
			System.out.println("Successfully register this student for this lab session");
			return true;
		}
		System.out.println("No available vacancies for this lab session");
		return false;
	}
	
	public int currentOccupied() { return this.students.size(); }
}
