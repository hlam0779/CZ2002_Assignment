import java.io.Serializable;

/**
 * Represent a professor in the school
 * @author Group7-SE1
 * @since 2018-11-09
 */
public class Professor implements Serializable {
	private static final long serialVersionUID = -3914670736074682579L;
	private String name ;
	private String email ;
	private int contact ;
	/**
	 * Create a professor with the name, email and contact number
	 * @param n		Professor's name
	 * @param e		Professor's email
	 * @param c		professor's contactNo
	 */
	public Professor(String n, String e, int c)  {
		name = n ;
		email = e ;
		contact = c ;
	}
	
	/**
	 * Get the name of this professor
	 * @return professor's name
	 */
	public String getName() { return name ; }
	
	/**
	 * Get the contact of this professor
	 * @return professor's contactNo
	 */
	public int getContact() { return contact ; }
	
	/**
	 * Get the email of this professor
	 * @return professor's email
	 */
	public String getEmail() { return email ; }

	/**
	 * Override the equals method of Object class
	 * Professor's name is unique
	 * @param o	an object
	 * @return boolean value indicating the equality of this professor and the other
	 */
	public boolean equals(Object o) {
		if (o instanceof Professor) {
			Professor p = (Professor)o;
			return (getName().equals(p.getName()));
		}
		return false;
	}
	
	/**
	 * Get the general information of professor
	 * @return message that mentions this professor information
	 */
	public String toString(){
		return name+"    ProfEmail: "+email+"    ContactNo: "+contact;
	}
}
