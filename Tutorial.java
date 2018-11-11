import java.util.HashSet;
import java.io.Serializable;

/**
 * Represent a tutorial of certain course in the school
 * @author Group7-SE1
 * @since 2018-11-09
 */
public class Tutorial implements Serializable{
	private static final long serialVersionUID = -6595084104792911570L;
	private Course course;
	private int tutId;
	private int capacity;
	private HashSet<Student> students;
	
	/**
	 * create a tutorial with the corresponding course, the tutorial index and the corresponding capacity
	 * @param course 	The course to which the tutorial belong to
	 * @param tutId	    This tutorial's index
	 * @param capacity	This tutorial's capacity
	 */
	public Tutorial(Course course, int tutId, int capacity) {
		this.course = course;
		this.tutId = tutId;
		this.students = new HashSet<Student>();
		this.capacity = capacity;
	}
	
	/**
	 * Get the index of this tutorial
	 * @return this tutorial's index
	 */
	public int getTutId() { return tutId; }
	
	/**
	 * Get the capacity of this tutorial
	 * @return this tutorial's capacity
	 */
	public int getCapacity() { return capacity; }
	
	/**
	 * Get the number of vacancies of this tutorial
	 * @return this tutorial's number of vacancies
	 */
	public int getVacancies() { return capacity-currentOccupied(); }
	
	/**
	 * Get the student list of this tutorial
	 * @return this tutorial's student list (using hashSet structure to represent)
	 */
	public HashSet<Student> getStudents() { return students; }
	
	/**
	 * Get the number of registered student of this tutorial
	 * @return this tutorial's registered students
	 */
	public int currentOccupied() { return this.students.size(); }
	
	/**
	 * Add a student to the student list of this tutorial 
	 * Return true if successfully add the student to this course and false otherwise
	 * @param student  student that need to be registered for this tutorial
	 * @return boolean value indicating the register status
	 */
	public boolean addStudent(Student student) {
		
		//Check vacancies of this tutorial
		if (capacity > currentOccupied()) {
			this.students.add(student);
			course.reduceTutVacancies();
			return true;
		}
		System.out.println("No available vacancies for this index\n");
		return false;
	}
	
	/**
	 * This is used for printing the number of vacancies of this tutorial in the checkVacancies method of the ManageApp
	 * @return message refer to this tutorial's number of vacancies
	 */
	@Override
	public String toString() {
		return "Total vacancies: "+this.getVacancies()+ "/"+capacity+"\n";
	}
}
