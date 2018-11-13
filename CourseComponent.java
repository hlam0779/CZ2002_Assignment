import java.util.HashSet;
import java.io.Serializable;

/**
 * Represent a component of certain course in the school
 * @author Group7-SE1
 * @since 2018-11-09
 */
public class CourseComponent implements Serializable{
	private static final long serialVersionUID = 1L;
	private int Id;
	private int capacity;
	private HashSet<Student> students;
	private String classType;
	/**
	 * create a tutorial with the tutorial index and the corresponding capacity
	 * @param course 	The course to which this component belong
	 * @param Id	    This course component's index
	 * @param capacity	This component's capacity
	 */
	public CourseComponent(int Id, int capacity, String classType) {
		this.Id = Id;
		this.students = new HashSet<Student>();
		this.capacity = capacity;
		this.classType = classType;
	}
	
	/**
	 * Get the index of this course component
	 * @return this course component's index
	 */
	public int getId() { return Id; }
	
	/**
	 * Get the capacity of this course component
	 * @return this component's capacity
	 */
	public int getCapacity() { return capacity; }
	
	/**
	 * Get the number of vacancies of this course component
	 * @return this course component's number of vacancies
	 */
	public int getVacancies() { return capacity-currentOccupied(); }
	
	/**
	 * Get the student list of this course component
	 * @return this course component's student list (using HashSet structure to represent)
	 */
	public HashSet<Student> getStudents() { return students; }
	
	/**
	 * Get the number of registered student of this course component
	 * @return this course component's registered students
	 */
	public int currentOccupied() { return this.students.size(); }
	
	/**
	 * Add a student to the student list of this class 
	 * Return true if successfully add the student to this course and false otherwise
	 * @param student  student that need to be registered for this class
	 * @return boolean value indicating the register status
	 */
	public boolean addStudent(Student s) {
		
		//Check vacancies
		if (this.getVacancies()>0) {
			this.getStudents().add(s);
			return true;
		}
		return false;
	}
	
	/**
	 * This is used for printing the number of vacancies of this class in the checkVacancies method of the ManageApp
	 * @return message refer to this class's number of vacancies
	 */
	@Override
	public String toString() {
		return "Total vacancies: "+this.getVacancies()+ "/"+capacity+"\n";
	}
}
