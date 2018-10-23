import java.util.HashSet;
import java.io.Serializable;

public class Lecture implements Serializable{
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
	
	public boolean addStudent(Student s) {
		if (capacity > currentOccupied()) {
			this.students.add(s);
			System.out.println("Successfully register this student for this lecture");
			return true;
		}
		System.out.println("No available vacancies for this lecture");
		return false;
	}
	
	public int currentOccupied() {
		return this.students.size();
	}
}
