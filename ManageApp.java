import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.io.*;


public class ManageApp {
	
	public static Data data;
	public static HashMap<String,Student> studentList;
	public static HashMap<String,Course> courseList;
	public static ArrayList<Professor> profList;
	private static Scanner scan;

	public static void main(String[] args) {
		String profFile = "professor.dat";
		String dataFile = "data.dat";
		
		//Read data from external soure or create one if file does not exist
		data = (Data) SerializeDB.readSerializedDataObject(dataFile);
		studentList = data.getStudentList();
		courseList = data.getCourseList();
		profList = (ArrayList<Professor>) SerializeDB.readSerializedListObject(profFile);
		scan = new Scanner(System.in);
		
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
			
			while (true) {		//Check for valid choice
				try {
					choice = scan.nextInt();
					if (choice<1 || choice >11) {
						System.out.println("Your choice must be between 1 and 11, please input again:");
						continue;
					}
					break;
				} catch (InputMismatchException e) {
					System.out.println("Invalid choice, you must input an integer between 1 and 11, please input your choice again:");
					scan.nextLine();
				}
			}
				
			
			switch(choice) {
				case 1: addStudent(); break;
				case 2: addCourse(); break;
				case 3: registerStudent(); break;
				case 4: checkVacancies(); break;
				case 5: printStudentList(); break;
				case 6: setAssessmentComponentsWeightage(); break;
				case 7: enterCourseworkMark(); break;
				case 8: enterExamMark(); break;
				case 9: printCourseStatistics(); break;
				case 10: printStudentTranscript(); break;
				default: System.out.println("Program terminated");
			}
			} while (choice >= 1 && choice<11);
		SerializeDB.writeSerializedDataObject(dataFile, data);
		scan.close();
		}
	
	public static void addStudent() {
		scan.nextLine();
		
		//Input student information to add to record
		System.out.println("Enter student name:");
		String name = scan.nextLine();
		System.out.println("Enter matric Number:");
		String matricNo = scan.nextLine();
		
		//Check if the student is already in the record
		if (studentList.containsKey(matricNo)) {
			System.out.println("This student may already be inserted before since the same matric number is found in the record\n");
			return;
		}
		
		
		System.out.println("Enter major:");
		String major = scan.nextLine();
		System.out.println("Enter year of birth:");
		int yearOfBirth,yearOfAdmission;
		while (true) {		//Check for valid input
			try {
				yearOfBirth = scan.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, you must input an integer, please input year of birth again:");
				scan.nextLine();
			}
		}
		
		System.out.println("Enter year of admission:");
		while (true) {		//Check for valid input
			try {
				yearOfAdmission = scan.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, you must input an integer, please input year of admission again:");
				scan.nextLine();
			}
		}
		
		studentList.put(matricNo,new Student(name,matricNo,yearOfBirth,yearOfAdmission,major));
		System.out.println("Successfully add this student to the record");
		System.out.println("The current student list:");
		for (Student s: studentList.values()) {
			System.out.println(s);
		}
		System.out.println();
	}
	
	public static void addCourse() {
		scan.nextLine();
		
		//Input course information to add to the record
		System.out.println("Enter course name:");
		String name;
		while (true) {
			name = scan.nextLine();
			for (Course i: courseList.values()) {
				if (name.equals(i.getName())) {
					System.out.println("This course may already be inserted before since the same course name is found in the record\n");
					return;
				}
			}
			break;
		}
		System.out.println("Enter courseCode:");
		String courseCode = scan.nextLine();
		if (courseList.containsKey(courseCode)) {	//Check whether the same course has already be inserted before
			System.out.println("This course may already be inserted before since the same course code is found in the record\n");
			return;
		}
		
		//Input professor name to assign to this course's coordinator
		String profName;
		Professor prof = null;
		boolean valid = false;  //indicating whether the input professor name is in the record
		while (true){		//Ensure the valid input of professor name
			System.out.println("Enter coordinator name:");
			profName = scan.nextLine();
			for (Professor i: profList) {
				if (i.getName().equals(profName)) {
					prof = i;
					valid = true;
					break;
				}
			}
			if (valid)
				break;
			else System.out.println("There is no professor whose name is "+profName+", please choose another name:");
		}
		
		//Input the course structure
		int courseStructure;
		while (true) {		//Check for valid input
			try {
				System.out.println("Choose course structure(1-3):");
				System.out.println("1.Course only has lectures");
				System.out.println("2.Course only has lectures and tutorial classes");
				System.out.println("3.Course has lectures, tutorial and laboratory sessions");
				if (scan.hasNextInt())
					courseStructure = scan.nextInt();
				else {
					scan.nextLine();
					throw new Exception("Invalid input, you must input an integer between 1 and 3");
				}
				if (courseStructure<1 || courseStructure >3)
					throw new Exception("Invalid choice, your choice must be between 1 and 3");
				break;
			} catch (Exception e){
				System.out.println(e.getMessage());
			}
		}
		
		//Input number of lectures and the corresponding capacity
		System.out.println("Enter the number of lectures for this course:");
		int noOfLecs = scan.nextInt();
		System.out.println("Enter the capacity for these lecture:");
		int lecCapacity = scan.nextInt();
		if (courseStructure == 1) {
			
			//Create new course object and add to course list
			courseList.put(courseCode, new Course(name, courseCode, prof, 1));
			courseList.get(courseCode).createLec(noOfLecs, lecCapacity);
		}
		else if (courseStructure == 2){
			
			//Input number of tutorials and the corresponding capacity
			System.out.println("Enter the number of tutorials for this course:");
			int noOfTuts = scan.nextInt();
			System.out.println("Enter the capacity for these tutorial:");
			int tutCapacity = scan.nextInt();
			
			//Create new course object and add to course list
			courseList.put(courseCode, new Course(name, courseCode, prof, 2));
			courseList.get(courseCode).createLec(noOfLecs, lecCapacity);
			courseList.get(courseCode).createTut(noOfTuts, tutCapacity);
		}
		else {
			
			//Input number of tutorials and the corresponding capacity
			System.out.println("Enter the number of tutorials for this course:");
			int noOfTuts = scan.nextInt();
			System.out.println("Enter the capacity for these tutorial:");
			int tutCapacity = scan.nextInt();
			
			//Input number of lectures and the corresponding capacity
			System.out.println("Enter the number of lab sessions for this course:");
			int noOfLabs = scan.nextInt();
			System.out.println("Enter the capacity for these lab sessions:");
			int labCapacity = scan.nextInt();
			
			//Create new course object and add to course list
			courseList.put(courseCode, new Course(name, courseCode, prof, 3));
			courseList.get(courseCode).createLec(noOfLecs, lecCapacity);
			courseList.get(courseCode).createTut(noOfTuts, tutCapacity);
			courseList.get(courseCode).createLab(noOfLabs, labCapacity);
		}
		
		//Input the grade weightage structure of this
		int weightStructure = 0 ;
		while (true) {		//Check for valid input
			System.out.println("Choose course grade weightage structure: ");
			System.out.println("1.Course has exam and coursework weightage without sub-component");
			System.out.println("2.Course has exam and coursework weightage with two sub-components");
			try {
				weightStructure = scan.nextInt();
				if (weightStructure > 2 || weightStructure <1) {
					System.out.println("Invalid choice, your choice must be either 1 or 2");
					continue;
				}		
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, your choice must be either 1 or 2");
				scan.nextLine();
			}
			courseList.get(courseCode).setWeightStructure(weightStructure);
			break;
		}
		System.out.println("Successfully add this course to the record");
		System.out.println("The current course list:");
		for (Course c: courseList.values()) {
			System.out.println(c.getCourseInfo());
		}
		System.out.println();
	}
	
	public static void registerStudent() {
		
		//Check whether there is any student to register
		if (studentList.size() == 0) {
			System.out.println("There is no students in the record to register, please add students first\n");
			return;
		}
		
		//Check whether there is any course to register
		if(courseList.size() == 0) {
			System.out.println("There is no courses in the record to register, please add courses first\n");
			return;
		}
		
		//Input the matric number of the corresponding student to register
		String matricNo, courseCode;
		System.out.println("Enter the student matric number:");
		scan.nextLine();
		while (true) {
			matricNo = scan.nextLine();
			if (! studentList.containsKey(matricNo)) {
				System.out.printf("There is no student whose matric number is %s, please input again:\n", matricNo);
				continue;
			}
			break;
		}
		Student student = studentList.get(matricNo);
		
		//Input the course code of the corresponding course to register
		System.out.println("Enter the course code:");
		while (true) {
			courseCode = scan.nextLine();
			if (!courseList.containsKey(courseCode)) {
				System.out.printf("There is no course whose course code is %s, please input again:\n", courseCode);
				continue;
			}
			break;
		}
		Course course = courseList.get(courseCode);
		
		//Check whether the student has already registered for this course
		if (course.getStudents().contains(student)) {
			System.out.println("This student has successfully registered this course before\n");
			return;
		}
		
		//Check vacancies and return if the course has no available vacancies
		if (! course.hasVacancies()) {
			System.out.println("This course has no more vacancies\n");
			return;
		}
		
		/*
		 * The remaining code segment will register student to specific index of course components
		 * based on the course structure
		 */
		
		//Input the lecture index to register
		System.out.printf("Enter the desired lecture index for this course (0-%d):\n", course.getLecs().size()-1);
		
		while (true) {		//Ask for input another lecture index if the lecture index has no vacancies or the index is invalid
			
			try {
				int lecId = scan.nextInt();
				if ( ! student.registerLec(courseCode, lecId)) {
					System.out.println("Please choose another index:");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid index, please choose a valid index:");
				scan.nextLine();
			}
		}
		
		//Return if the course only has lectures
		if (course.getCourseStructure() == 1)
			return;
		
		//Input the tutorial index to register
		System.out.printf("Enter the desired tutorial index for this course (0-%d):\n", course.getTuts().size()-1);
		
		while (true) {		//Ask for input another tutorial index if the tutorial index has no vacancies or the index is invalid
			
			try {
				int tutId = scan.nextInt();
				if ( ! student.registerTut(courseCode, tutId) ) {
					System.out.println("Please choose another index:");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid index, please choose a valid index:");
				scan.nextLine();
			}
		}
		
		//Return if the course only has lectures and tutorials
		if (course.getCourseStructure() == 2)
			return;
		
		//Input the lab index to register
		System.out.printf("Enter the desired lab session index for this course (0-%d):\n", course.getLabs().size()-1);
		
		while (true) {		//Ask for input another lab index if the lab index has no vacancies or the index is invalid
			
			try {
				int labId = scan.nextInt();
				if ( ! student.registerLab(courseCode, labId)) {
					System.out.println( "Please choose another index:");
					continue;
				}
				break;
			}catch (InputMismatchException e){
				System.out.println("Invalid index, please choose a valid index:");
				scan.nextLine();
			}
		}
	}
	
	public static void checkVacancies() {
		
		//Check whether there is any course in the record
		if (courseList.size() == 0) {
			System.out.println("There is no courses in the record, please add courses first\n");
			return;
		}
		
		//Input the course code of the corresponding course to check
		System.out.println("Enter course code:");
		scan.nextLine();
		String courseCode;
		while (true) {
			courseCode = scan.nextLine();
			if (!courseList.containsKey(courseCode)) {
				System.out.printf("There is no course whose course code is %s, please input again:\n", courseCode);
				continue;
			}
			break;
		}
		Course course = courseList.get(courseCode);

		
		//Choose the class type of the course to check vacancies
		int choice;
		while (true) {		//Keep displaying the class type to check vacancies as long as user does not want to exit
			System.out.println("Choose the type of class that you want to check vacancies:");
			System.out.println("1.Lecture");
			System.out.println("2.Tutorial");
			System.out.println("3.Lab");
			System.out.println("4.Exit");
			
			while (true) {		//Check for valid choice
				try {
					choice = scan.nextInt();
					if (choice<1 || choice >4) {
						System.out.println("your choice must be between 1 and 4, please input another choice:");
						continue;
					}
					break;
				} catch (InputMismatchException e) {
					System.out.println("Invalid input, please input again:");
					scan.nextLine();
				}
			}
			if (choice == 4) break;
			
			else if(choice == 1) {
				
				//Input the lecture index to check vacancies
				System.out.printf("Enter the lecture index you want to check (0-%d):\n", course.getLecs().size()-1);
				int index;
				
				while (true) {		//Check for valid index
					try {
						index = scan.nextInt();
						if (index<0 || index>= course.getLecs().size()) {
							System.out.println("There is no such index, please choose another index:");
							continue;
						}
						break;
					} catch (InputMismatchException e) {
						System.out.println("Invalid input, please input again:");
						scan.nextLine();
					}
				}
				System.out.println(course.getLecs().get(index));
			}
			else if(choice == 2) {
				
				//Check if this course has tutorial classes
				if (course.getCourseStructure() == 1) {
					System.out.println("This course has no tutorials");
					continue;
				}
				
				//Input the tutorial index
				System.out.printf("Enter the tutorial index you want to check (0-%d):\n", course.getTuts().size()-1);
				int index;
				
				while (true) {		//Check for valid index
					try {
						index = scan.nextInt();
						if (index<0 || index>= course.getTuts().size()) {
							System.out.println("There is no such index, please choose another index:");
							continue;
						}
						break;
					} catch (InputMismatchException e) {
						System.out.println("Invalid input, please input again:");
						scan.nextLine();
					}
				}
				System.out.println(course.getTuts().get(index));
			}
			else {
				
				//Check if this course has lab sessions
				if (course.getCourseStructure() != 3) {
					System.out.println("This course has no lab sessions\n");
					continue;
				}
				
				//Input lab index
				System.out.printf("Enter the lab session index you want to check (0-%d):\n", course.getLabs().size()-1);
				int index;
				
				while (true) {		//Check for valid index
					try {
						index = scan.nextInt();
						if (index<0 || index>= course.getLabs().size()) {
							System.out.println("There is no such index, please choose another index:");
							continue;
						}
						break;
					} catch (InputMismatchException e) {
						System.out.println("Invalid input, please input again:");
						scan.nextLine();
					}
				}
				System.out.println(course.getLabs().get(index));
			}
			
		}
	}
	
	public static void printStudentList() {
		
		//Check whether there is any course in the record
		if (courseList.size() == 0) {
			System.out.println("There is no courses in the record, please add courses first\n");
			return;
		}
		
		//Input the course code of the corresponding course to print student list
		System.out.println("Enter the course code:");
		scan.nextLine();
		String courseCode;
		while (true) {
			courseCode = scan.nextLine();
			if (!courseList.containsKey(courseCode)) {
				System.out.printf("There is no course whose course code is %s, please input again:\n", courseCode);
				continue;
			}
			break;
		}
		Course course = courseList.get(courseCode);
		
		//Choose the class type of this course to print student list
		int choice;
		
		while (true) {		//Keep displaying the class type to print the student list as long user does not want to exit
			
			System.out.println("Choose the type of class that you want to print the student list:");
			System.out.println("1.Lecture");
			System.out.println("2.Tutorial");
			System.out.println("3.Lab");
			System.out.println("4.Exit");
			
			while (true) {		//Check for valid choice
				try {
					choice = scan.nextInt();
					if (choice < 1 || choice >4) {
						System.out.println("your choice must be between 1 and 4, please input another choice:");
						continue;
					}
					break;
				} catch (InputMismatchException e) {
					System.out.println("Invalid choice, please input again:");
					scan.nextLine();
				}
			}
			if (choice == 1) {
				
				//Input the lecture index to print student list
				System.out.printf("Enter the lecture index you want to print the student list (0-%d):\n", course.getLecs().size()-1);
				int index;
				
				while (true) {		//Check for valid index
					try {
						index = scan.nextInt();
						if (index<0 || index>= course.getLecs().size()) {
							System.out.println("There is no such index, please choose another index:");
							continue;
						}
						break;
					} catch (InputMismatchException e) {
						System.out.println("Invalid input, please input again:");
						scan.nextLine();
					}
				}
				Lecture lecture = course.getLecs().get(index);
				
				//Check if no student registers for this lecture index
				if (lecture.getStudents().size() == 0)
					System.out.println("There is no student in this lecture\n");
				else {
					for (Student i: lecture.getStudents()) {
						System.out.println(i);
					}
					System.out.println();
				}
				
			}else if (choice == 2) {
				
				//Check if this course has tutorial classes
				if (course.getCourseStructure() == 1) {
					System.out.println("This course has no tutorials\n");
					continue;
				}
				
				//Input tutorial index to print student list
				System.out.printf("Enter the tutorial index you want to print the student list (0-%d):\n", course.getTuts().size()-1);
				int index;
				
				while (true) {		//Check for valid index
					try {
						index = scan.nextInt();
						if (index<0 || index>= course.getTuts().size()) {
							System.out.println("There is no such index, please choose another index:");
							continue;
						}
						break;
					} catch (InputMismatchException e) {
						System.out.println("Invalid input, please input again:");
						scan.nextLine();
					}
				}
				Tutorial tut = course.getTuts().get(index);
				
				//Check if no student registers for this tutorial index
				if (tut.getStudents().size() == 0)
					System.out.println("There is no student in this tutorial\n");
				else {
					for (Student i: tut.getStudents()) {
						System.out.println(i);
					}
					System.out.println();
				}
				
			}else if (choice == 3) {
				
				//Check if this course has lab sessions
				if (course.getCourseStructure() != 3) {
					System.out.println("This course has no lab sessions\n");
					continue;
				}
				
				//Input lab index
				System.out.printf("Enter the lab index you want to print the student list (0-%d):\n", course.getLabs().size()-1);
				int index;
				
				while (true) {		//Check for valid index
					try {
						index = scan.nextInt();
						if (index<0 || index>= course.getLabs().size()) {
							System.out.println("There is no such index, please choose another index:");
							continue;
						}
						break;
					} catch (InputMismatchException e) {
						System.out.println("Invalid input, please input again:");
						scan.nextLine();
					}
				}
				Lab lab = course.getLabs().get(index);
				
				//Check if no student registers for this lab index
				if (lab.getStudents().size() == 0)
					System.out.println("There is no student in this lab session\n");
				else {
					for (Student i: lab.getStudents()) {
						System.out.println(i);
					}
					System.out.println();
				}
			}else break;
		}
	}
	
	public static void setAssessmentComponentsWeightage() {
		
		//Check whether there is any course in the record
		if (courseList.size() == 0) {
			System.out.println("There is no courses in the record, please add courses first\n");
			return;
		}
		
		//Input the course code of the corresponding course to set assessment weight
		System.out.println("Enter the course code:");
		scan.nextLine();
		String courseCode;
		
		while (true) {		//Check for valid course code
			courseCode = scan.nextLine();
			if (!courseList.containsKey(courseCode)) {
				System.out.printf("There is no course whose course code is %s, please input again:\n", courseCode);
				continue;
			}
			break;
		}
		Course course = courseList.get(courseCode);
		
		//Input assessment weight for coursework
		System.out.println("Enter the assessment weight of coursework(0-100):");
		double courseWorkWeight;
		
		while (true) {		//Check for valid weight
			try {
				courseWorkWeight = scan.nextDouble();
				if (courseWorkWeight<0 || courseWorkWeight>100) {
					System.out.println("The weight of coursework must be in range (0-100), please input again:");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, please input the weight again:");
				scan.nextLine();
			}
		}
		course.setCourseworkWeight(courseWorkWeight);
		
		//Check if this course's coursework has sub-component weightage or not
		if (course.getWeightStructure() == 1) {
			System.out.println("Successfully set assessment weight of coursework and exam\n");
			return;
		}
		//Input weight for assignment
		System.out.println("Enter the assignment weight of the coursework:");
		double assignmentWeight;
		
		while (true) {		//Check for valid weight
			try {
				assignmentWeight = scan.nextDouble();
				if (assignmentWeight<0 || assignmentWeight>100) {
					System.out.println("The weight of assignment must be in range (0-100)");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, please input the weight again:");
				scan.nextLine();
			}
		}
		course.setAssignmentWeight(assignmentWeight);
		System.out.println("Successfully set assessment weight of coursework, exam and coursework's component\n");
	}
	
	public static void enterCourseworkMark() {
		
		//Check whether there is any student in the record
		if (studentList.size() == 0) {
			System.out.println("There is no students in the record, please add students first\n");
			return;
		}
		
		//Check whether there is any course in the record
		if (courseList.size() == 0) {
			System.out.println("There is no courses in the record, please add courses first\n");
			return;
		}
		
		//Input matric number of the corresponding student
		System.out.println("Enter the student matric number to enter grade:");
		scan.nextLine();
		String matricNo;
		while (true) {
			matricNo = scan.nextLine();
			if (!studentList.containsKey(matricNo)) {
				System.out.printf("There is no student whose matric number is %s, please input again:\n", matricNo);
				continue;
			}
			break;
		}
		Student student = studentList.get(matricNo);
		
		//Input course code of the corresponding course
		System.out.println("Enter the corresponding course code to enter mark:");
		String courseCode;
		while (true) {
			courseCode = scan.nextLine();
			if (!courseList.containsKey(courseCode)) {
				System.out.printf("There is no course whose course code is %s, please input again:\n", courseCode);
				continue;
			}
			break;
		}
		Course course = courseList.get(courseCode);
		
		HashMap<String, GradeRecord> transcript = student.getTranscript();
		
		//Check the whether the student register for the input course
		if (! transcript.containsKey(courseCode)) {
			System.out.printf("This student does not register for the course %s\n\n", course);
			return;
		}
		GradeRecord gradeRecord = transcript.get(courseCode);
		
		/*
		 * The remaining code segment will ask user to enter coursework mark base on
		 * this course grade weightage structure
		 */
		double grade = 0;
		
		//Check if this course's coursework does not have sub-components
		if (course.getWeightStructure() == 1) {
			System.out.println("Enter the coursework grade (max 100):");
			
			while (true) {		//Check for valid grade
				try {
					grade = scan.nextDouble();
					if (grade<0 || grade >100) {
						System.out.println("Invalid input, the grade must be between 0 and 100, please input again:");
						continue;
					}
				} catch (InputMismatchException e) {
					System.out.println("Invalid input, you must input a real number, please input again:");
					scan.nextLine();
				}
				break;
			}
			gradeRecord.setCoursework(grade);
			System.out.println("Successfully enter this course's coursework grade for this student\n");
			return;
		}
		//Enter grade for assignment
		System.out.printf("Enter the assignment grade (max 100):\n", course.getAssignmentWeight());
		
		while (true) {		//Check for valid grade
			try {
				grade = scan.nextDouble();
				if (grade<0 || grade>100) {
					System.out.println("Invalid grade, the grade must be between 0 and 100, please input again:");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, you must input a real number, please input again:");
				scan.nextLine();
			}
		}
		gradeRecord.setAssigment(grade);
		
		//Enter grade for class-participation
		System.out.printf("Enter the class participation grade (max 100):\n", course.getClassParticipationWeight());
		
		while (true) {		//Check for valid grade
			try {
				grade = scan.nextDouble();
				if (grade<0 || grade>100) {
					System.out.println("Invalid grade, the grade must be between 0 and 100, please input again:");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, you must input a real number, please input again:");
				scan.nextLine();
			}
		}
		gradeRecord.setClassParticipation(grade);
		System.out.println("Successfully enter this course's coursework components grade for this student\n");
	}
	
	public static void enterExamMark() {
		
		//Check whether there is any student in the record
		if (studentList.size() == 0) {
			System.out.println("There is no students in the record, please add students first\n");
			return;
		}
		//Check whether there is any course in the record
		if (courseList.size() == 0) {
			System.out.println("There is no courses in the record, please add courses first\n");
			return;
		}
		
		//Input matric number of the corresponding student
		System.out.println("Enter the student matric number to enter grade:");
		scan.nextLine();
		String matricNo;
		
		while (true) {		//Check for valid matric number
			matricNo = scan.nextLine();
			if (!studentList.containsKey(matricNo)) {
				System.out.printf("There is no student whose matric number is %s, please input again:\n", matricNo);
				continue;
			}
			break;
		}
		Student student = studentList.get(matricNo);
		
		//Input course code of the corresponding course
		System.out.println("Enter the corresponding course code to enter mark:");
		String courseCode;
		
		while (true) {		//Check for valid course code
			courseCode = scan.nextLine();
			if (!courseList.containsKey(courseCode)) {
				System.out.printf("There is no course whose course code is %s, please input again:\n", courseCode);
				continue;
			}
			break;
		}
		
		//Check whether the student registers for the input course
		HashMap<String, GradeRecord> transcript = student.getTranscript();
		if (! transcript.containsKey(courseCode)) {
			System.out.printf("This student does not register for the course %s\n\n", courseList.get(courseCode));
			return;
		}
		GradeRecord gradeRecord = transcript.get(courseCode);
		
		//Enter exam grade
		System.out.println("Enter the exam grade (max 100):");
		double grade;
		
		while (true) {		//Check for valid grade
			try {
				grade = scan.nextDouble();
				if (grade<0 || grade>100) {
					System.out.println("Invalid grade, the grade must be between 0 and 100, please input again:");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, you must input a real number, please input again:");
				scan.nextLine();
			}
		}
		gradeRecord.setExam(grade);
		System.out.println("Successfully enter this course's exam grade of this student\n");
	}
	
	public static void printCourseStatistics() {
		scan.nextLine();
		
		//Check whether there is any course in the record
		if (courseList.size() == 0) {
			System.out.println("There is no courses in the record, please add courses first\n");
			return;
		}
		
		//Input the course code to print statistics
		System.out.println("Enter the course code:");
		String courseCode;
		
		while (true) {		//Check for valid course code
			courseCode = scan.nextLine();
			if (! courseList.containsKey(courseCode)) {
				System.out.printf("There is no course whose course code is %s, please input again:\n", courseCode);
				continue;
			}
			break;
		}
		
		/*
		 * The remaining code segment try to print the course statistics, namely the course name, course code, the coordinator name, 
		 * total number of registered students, total vacancies, the course structure and its corresponding components' statistics,
		 * the overall grade, exam grade and coursework grade percentage
		 */
		Course course = courseList.get(courseCode);
		System.out.println("Name: "+course.getName()+"    CourseCode: "+course.getCourseCode()+"    Coordinator: Prof "+course.getCoordinator());
		System.out.println("Total number of students taken this course: "+course.getStudents().size());
		System.out.println("Total vacancies: "+course.getVancancies());
		if (course.getCourseStructure() == 1) {
			System.out.println("This course only has lectures");
			System.out.printf("Number of lectures: %-9d",course.getLecs().size());
			System.out.println("Lecture capacity: "+course.getLecs().get(0).getCapacity());
		} else if(course.getCourseStructure() == 2) {
			System.out.println("This course only has lectures and tutorials");
			System.out.printf("Number of lectures: %-9d",course.getLecs().size());
			System.out.println("Lecture capacity: "+course.getLecs().get(0).getCapacity());
			System.out.printf("Number of tutorials: %-8d",course.getTuts().size());
			System.out.println("Tutorial capacity: "+course.getTuts().get(0).getCapacity());
		} else {
			System.out.println("This course has lectures, tutorials and lab sessions");
			System.out.printf("Number of lectures: %-9d",course.getLecs().size());
			System.out.println("Lecture capacity: "+course.getLecs().get(0).getCapacity());
			System.out.printf("Number of tutorials: %-8d",course.getTuts().size());
			System.out.println("Tutorial capacity: "+course.getTuts().get(0).getCapacity());
			System.out.printf("Number of lab sessions: %-5d",course.getLabs().size());
			System.out.println("Lab capacity: "+course.getLabs().get(0).getCapacity());
		}
		
		HashSet<Student> students = course.getStudents();
		if (students.size() == 0) {
			System.out.println("No grade statistics is available\n");
			return;
		}
		/*
		 * Calculate the grade percentage, there are five groups of grade and 
		 * one unknown group which indicates student whose grade has not been updated yet
		 */
		System.out.println();
		System.out.println("The below table shows how the percentage of students is distributed among groups of grade for each course component");
		double first = 0;
		double second = 0;
		double third = 0;
		double fourth = 0;
		double fifth = 0;
		double firstPercentage,secondPercentage,thirdPercentage,fourthPercentage, fifthPercentage, unknown;
		
		for (Student s: students) {
			HashMap<String,GradeRecord> transcript = s.getTranscript();
			GradeRecord gradeRecord = transcript.get(courseCode);
			if (course.getWeightStructure() == 2) {
				if (gradeRecord.updateCoursework()) {
					if(gradeRecord.getCoursework()>=80)
						first++;
					else if (gradeRecord.getCoursework()>=60) 
						second++;
					else if (gradeRecord.getCoursework()>=40)
						third++;
					else if (gradeRecord.getCoursework()>=20)
						fourth++;
					else fifth++;
				}
			}
			else if (gradeRecord.alreadyEnterCourseworkGrade){
				if(gradeRecord.getCoursework()>=80)
					first++;
				else if (gradeRecord.getCoursework()>=60) 
					second++;
				else if (gradeRecord.getCoursework()>=40)
					third++;
				else if (gradeRecord.getCoursework()>=20)
					fourth++;
				else fifth++;
			}
		}
		
		firstPercentage = first/students.size()*100.0;
		secondPercentage = second/students.size()*100.0;
		thirdPercentage = third/students.size()*100.0;
		fourthPercentage = fourth/students.size()*100.0;
		fifthPercentage = fifth/students.size()*100.0;
		unknown = 100 - firstPercentage - secondPercentage - thirdPercentage - fourthPercentage - fifthPercentage;
		
		System.out.println("GradeGroup       80-100     60-80     40-60     20-40     0-20     unknown");
		System.out.printf( "Coursework(%%)    %-9.1f  %-8.1f  %-8.1f  %-8.1f  %-7.1f  %.1f\n", firstPercentage, secondPercentage, 
														thirdPercentage, fourthPercentage, fifthPercentage, unknown);
		
		first = second = third = fourth = fifth = 0.0;
		for (Student s: students) {
			HashMap<String,GradeRecord> transcript = s.getTranscript();
			GradeRecord gradeRecord = transcript.get(courseCode);
			if (gradeRecord.alreadyEnterExamGrade) {
				if(gradeRecord.getExam()>=80)
					first++;
				else if (gradeRecord.getExam()>=60) 
					second++;
				else if (gradeRecord.getExam()>=40)
					third++;
				else if (gradeRecord.getExam()>=20)
					fourth++;
				else fifth++;
			}
		}
		
		firstPercentage = first/students.size()*100.0;
		secondPercentage = second/students.size()*100.0;
		thirdPercentage = third/students.size()*100.0;
		fourthPercentage = fourth/students.size()*100.0;
		fifthPercentage = fifth/students.size()*100.0;
		unknown = 100 - firstPercentage - secondPercentage - thirdPercentage - fourthPercentage - fifthPercentage;
		
		System.out.printf( "Exam(%%)          %-9.1f  %-8.1f  %-8.1f  %-8.1f  %-7.1f  %.1f\n", firstPercentage, secondPercentage, 
														thirdPercentage, fourthPercentage, fifthPercentage, unknown);
		
		first = second = third = fourth = fifth = 0.0;
		for (Student s: students) {
			HashMap<String,GradeRecord> transcript = s.getTranscript();
			GradeRecord gradeRecord = transcript.get(courseCode);
			if (gradeRecord.calOverallGrade()) {
				if(gradeRecord.getOverallGrade()>=80)
					first++;
				else if (gradeRecord.getOverallGrade()>=60) 
					second++;
				else if (gradeRecord.getOverallGrade()>=40)
					third++;
				else if (gradeRecord.getOverallGrade()>=20)
					fourth++;
				else fifth++;
			}
		}
		
		firstPercentage = first/students.size()*100.0;
		secondPercentage = second/students.size()*100.0;
		thirdPercentage = third/students.size()*100.0;
		fourthPercentage = fourth/students.size()*100.0;
		fifthPercentage = fifth/students.size()*100.0;
		unknown = 100 - firstPercentage - secondPercentage - thirdPercentage - fourthPercentage - fifthPercentage;
		
		System.out.printf( "OverallGrade(%%)  %-9.1f  %-8.1f  %-8.1f  %-8.1f  %-7.1f  %.1f\n", firstPercentage, secondPercentage, 
														thirdPercentage, fourthPercentage, fifthPercentage, unknown);
		System.out.println();
	}
	
	public static void printStudentTranscript() {
		
		//Check if there is any student in the record
		if (studentList.size() == 0) {
			System.out.println("There is no student in the record, please add student first:");
			return;
		}
		
		//Input student matric number
		System.out.println("Enter the student matric number:");
		scan.nextLine();
		String matricNo;
		
		while (true) {		//Check for valid matric number
			matricNo = scan.nextLine();
			if (!studentList.containsKey(matricNo)) {
				System.out.printf("There is no student whose matric number is %s, please input again:\n", matricNo);
				continue;
			}
			break;
		}
		Student student = studentList.get(matricNo);
		
		HashMap<String,GradeRecord> transcript = student.getTranscript();
		if (transcript.values().size() == 0) {
			System.out.println("This student has not registered for any course yet");
			return;
		}
		//Print grade records for each course in the transcript
		for (GradeRecord i: transcript.values()) {
			System.out.println(i);
		}
		System.out.println();
	}
}
