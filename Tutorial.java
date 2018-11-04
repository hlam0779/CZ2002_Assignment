import java.util.HashSet;
import java.io.Serializable;

public class Tutorial implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6595084104792911570L;
	private Course course;
	private int tutId;
	private int capacity;
	private HashSet<Student> students;
	
	public Tutorial(Course course, int tutId, int capacity) {
		this.course = course;
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
		
		//Check vacancies of this tutorial
		if (capacity > currentOccupied()) {
			this.students.add(s);
			course.reduceTutVacancies();
			return true;
		}
		System.out.println("No available vacancies for this index\n");
		return false;
	}
	
	//This is used for printing the vacancies of this tutorial in the checkVacancies method of the ManageApp
	@Override
	public String toString() {
		return "Total vacancies: "+ (capacity-currentOccupied())+ "/"+capacity+"\n";
	}
}
