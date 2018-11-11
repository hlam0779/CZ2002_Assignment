import java.util.HashSet;
import java.io.Serializable;

/**
 * Represent a lecture of certain course in the school
 * @author Group7-SE1
 * @since 2018-11-09
 */
public class Lecture implements Serializable{
	private static final long serialVersionUID = 5281074533738230052L;
	private Course course;
	private int lecId;
	private int capacity;
	private HashSet<Student> students;
	
	/**
	 * create a lecture with the corresponding course, the lecture index and the corresponding capacity
	 * @param course
	 * @param lecId
	 * @param capacity
	 */
	public Lecture(Course course, int lecId, int capacity) {
		this.course = course;
		this.lecId = lecId;
		this.students = new HashSet<Student>();
		this.capacity = capacity;
	}
	
	/**
	 * Get the index of this lecture
	 * @return this lecture's index
	 */
	public int getLecId() { return lecId; }
	
	/**
	 * Get the capacity of this lecture
	 * @return this lecture's capacity
	 */
	public int getCapacity() { return capacity; }
	
	/**
	 * Get the number of vacancies of this lecture
	 * @return this lecture's number of vacancies
	 */
	public int getVacancies() { return capacity-currentOccupied(); }
	
	/**
	 * Get the student list of this lecture
	 * @return this lecture's student list (using hashSet structure to represent)
	 */
	public HashSet<Student> getStudents() { return students; }
	
	/**
	 * Get the number of registered student of this lecture
	 * @return this lecture's registered students
	 */
	public int currentOccupied() { return this.students.size(); }
	
	/**
	 * Add a student to the student list of this lecture 
	 * Return true if successfully add the student to this course and false otherwise
	 * @param student  student that need to be registered for this lecture
	 * @return boolean value indicating the register status
	 */
	public boolean addStudent(Student s) {
		
		//Check vacancies
		if (capacity > currentOccupied()) {
			this.students.add(s);
			course.reduceLecVacancies();
			System.out.println("Successfully register this student for this lecture\n");
			return true;
		}
		System.out.println("No available vacancies for this lecture\n");
		return false;
	}
	
	/**
	 * This is used for printing the number of vacancies of this lecture in the checkVacancies method of the ManageApp
	 * @return message refer to this lecture's number of vacancies
	 */
	@Override
	public String toString() {
		return "Total vacancies: "+ this.getVacancies()+ "/"+capacity+"\n";
	}
}
