import java.util.HashSet;
import java.io.Serializable;

/**
 * Represent a lab session of certain course in the school
 * @author Group7-SE1
 * @since 2018-11-09
 */
public class Lab implements Serializable{
	private static final long serialVersionUID = -9019537250606693375L;
	private Course course;
	private int labId;
	private HashSet<Student> students;
	private int capacity;
	
	/**
	 * create a lab session with the corresponding course, the lab index and the corresponding capacity
	 * @param course
	 * @param labId
	 * @param capacity
	 */
	public Lab(Course course, int labId, int capacity) {
		this.course = course;
		this.labId = labId;
		this.capacity = capacity;
		this.students = new HashSet<Student>();
	}
	
	/**
	 * Get the index of this lab
	 * @return this lab's index
	 */
	public int getLabId() { return labId; }
	
	/**
	 * Get the capacity of this lab
	 * @return this lab's capacity
	 */
	public int getCapacity() { return capacity; }
	
	/**
	 * Get the number of vacancies of this lab
	 * @return this lab's number of vacancies
	 */
	public int getVacancies() { return capacity-currentOccupied(); }
	
	/**
	 * Get the student list of this lab
	 * @return this lab's student list (using hashSet structure to represent)
	 */
	public HashSet<Student> getStudents() { return students; }
	
	/**
	 * Get the number of registered student of this lab
	 * @return this lab's registered students
	 */
	public int currentOccupied() { return this.students.size(); }
	
	/**
	 * Add a student to the student list of this lab 
	 * Return true if successfully add the student to this course and false otherwise
	 * @param student  student that need to be registered for this lab
	 * @return boolean value indicating the register status
	 */
	public boolean addStudent(Student student) {
		
		//Check vacancies
		if (capacity > currentOccupied()) {
			this.students.add(student);
			course.reduceLabVacancies();
			return true;
		}
		System.out.println("No available vacancies for this index\n");
		return false;
	}
	
	/**
	 * This is used for printing the number of vacancies of this lab in the checkVacancies method of the ManageApp
	 * @return message refer to this lab's number of vacancies
	 */
	@Override
	public String toString() {
		return "Total vacancies: "+ this.getVacancies()+ "/"+capacity+"\n";
	}
}
