import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;


public class ManageApp {
	
	public static void main(String[] args) {
		HashMap<Integer,Student> studentList = new HashMap<Integer,Student>();
		HashMap<String,Course> courseList = new HashMap<String, Course>();
		ArrayList<Professor> profList = readSerializedObject("professor.dat");
		String name, major, NRIC;
		int yearOfBirth, yearOfAdmission;
		Scanner sc = new Scanner(System.in);
		int choice;
		do {
			System.out.println("1.Add a student");
			System.out.println("2.Add a course");
			System.out.println("3.Register student for course");
			System.out.println("4.Check available slot in class");
			System.out.println("5.Print student list by lecture, tutorial or laboratory session for a course");
			System.out.println("6.Enter course assessment components weightage");
			System.out.println("7.Enter coursework mark – inclusive of its components");
			System.out.println("8.Enter exam mark");
			System.out.println("9.Print course statistics");
			System.out.println("10.Print student transcript");
			System.out.println("11.Exit");
			choice = sc.nextInt();
			
			switch(choice) {
			case 1: 
				while (true) {
					System.out.println("Enter student name:");
					name = sc.nextLine();
					System.out.println("Enter NRIC:");
					NRIC = sc.nextLine();
					if (studentList.containsKey(NRIC)) {
						System.out.println("This student may already be inserted before since the same NRIC is found in the record");
						break;
					}
				}
				System.out.println("Enter major:");
				major = sc.nextLine();
				System.out.println("Enter year of birth:");
				yearOfBirth = sc.nextInt();
				System.out.println("Enter year of admission:");
				yearOfAdmission = sc.nextInt();
				studentList.put(Student.noOfStudents+1,new Student(name,NRIC,yearOfBirth,yearOfAdmission,NRIC));
				break;
			case 2: addCourse(courseList, profList); break;
			case 3: //registerStudent(int studentId, String courseCode); break;
			case 4: //check(String courseCode); break;
			case 5: //printStudentList(String courseCode); break;
			case 6: //enterAssessmentComponentsWeightage(String courseCode); break;
			case 7: //enterCourseworkMark(int studentId, String courseCode); break;
			case 8: //enterExamMark(int studentId, String courseCode); break;
			case 9: //printCourseStatistic(String courseCode); break;
			case 10: //printTranscript(int studentId); break;
			default: //System.out.println("Program terminating...");
			}
			} while (choice >= 1 && choice<11);
		}
	public static ArrayList readSerializedObject(String filename) {
		ArrayList pDetails = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			pDetails = (ArrayList) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		// print out the size
		//System.out.println(" Details Size: " + pDetails.size());
		//System.out.println();
		return pDetails;
	}
	
	public static void addCourse(HashMap<String, Course> s, ArrayList<Professor> p) {
		Scanner scan = new Scanner(System.in);
		int courseStructure;
		boolean valid = false;
		String profName;
		Professor prof = null;
		System.out.println("Enter course name:");
		String name = scan.nextLine();
		System.out.println("Enter courseCode:");
		String courseCode = scan.nextLine();
		while (true){
			System.out.println("Enter cordinator name:");
			profName = scan.nextLine();
			for (Professor i: p) {
				if (i.getName().equals(profName)) {
					prof = i;
					valid = true;
					break;
				}
			}
			if (valid)
				break;
			else System.out.println("There is no professor whose name is: "+profName);
		}
		
		while (true) {
			try {
				System.out.println("Choose course structure(1-3):");
				System.out.println("1.Course only has lectures");
				System.out.println("2.Course only has lectures and tutorial classes");
				System.out.println("3.Course has lectures, tutorial and laboratory sessions");
				if (scan.hasNextInt())
					courseStructure = scan.nextInt();
				else throw new Exception("Invalid choice");
				if (courseStructure<1 || courseStructure >3)
					throw new Exception("Invalid choice");
				break;
			} catch (Exception e){
				System.out.println(e.getMessage());
			}
		}
		
		if (courseStructure == 1) {
			s.put(courseCode, new Course(name, courseCode, prof, courseStructure));
			System.out.println("Enter the number of lectures for this course:");
			int noOfLecs = scan.nextInt();
			System.out.println("Enter the capacity for these lecture:");
			int capacity = scan.nextInt();
			s.get(courseCode).createLec(noOfLecs, capacity);
		}
		else if (courseStructure == 2){
			s.put(courseCode,  new Course(name, courseCode, prof, courseStructure));
			s.put(courseCode, new Course(name, courseCode, prof, courseStructure));
			System.out.println("Enter the number of lectures for this course:");
			int noOfLecs = scan.nextInt();
			System.out.println("Enter the capacity for these lectures:");
			int capacity = scan.nextInt();
			s.get(courseCode).createLec(noOfLecs, capacity);
			System.out.println("Enter the number of tutorials for this course:");
			int noOfTut = scan.nextInt();
			System.out.println("Enter the capacity for these tutorial");
			capacity = scan.nextInt();
			s.get(courseCode).createTut(noOfTut, capacity);
		}
		else {
			s.put(courseCode,  new Course(name, courseCode, prof, courseStructure));
			s.put(courseCode, new Course(name, courseCode, prof, courseStructure));
			System.out.println("Enter the number of lectures for this course:");
			int noOfLecs = scan.nextInt();
			System.out.println("Enter the capacity for these lectures:");
			int capacity = scan.nextInt();
			s.get(courseCode).createLec(noOfLecs, capacity);
			System.out.println("Enter the number of tutorials for this course:");
			int noOfTut = scan.nextInt();
			System.out.println("Enter the capacity for these tutorial");
			capacity = scan.nextInt();
			s.get(courseCode).createTut(noOfTut, capacity);
			System.out.println("Enter the number of lab sessions for this course:");
			int noOfLab = scan.nextInt();
			System.out.println("Enter the capacity for these lab sessions:");
			s.get(courseCode).createLab(noOfLab, capacity);
			
		}
		scan.close();
	}
	
	public static void registerStudent(int matricNo, Course course) {
		
	}
}
