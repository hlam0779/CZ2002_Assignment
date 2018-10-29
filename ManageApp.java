import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
import java.util.HashMap;
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
			while (true) {
				try {
					choice = scan.nextInt();
					if (choice<1 || choice >11) {
						System.out.println("Your choice must be between 1 and 11, please input again:");
						continue;
					}
					break;
				} catch (InputMismatchException e) {
					System.out.println("Invalid choice, please input your choice again:");
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
		boolean valid = false;	//indicating whether the student is already in the record
		
		//Input student information to add to record
		System.out.println("Enter student name:");
		scan.nextLine();
		String name = scan.nextLine();
		System.out.println("Enter matric Number:");
		String matricNo = scan.nextLine();
		if (studentList.containsKey(matricNo)) {
			System.out.println("This student may already be inserted before since the same matric number is found in the record\n");
			valid = true;
		}
		
		if (valid) return;
		
		System.out.println("Enter major:");
		String major = scan.nextLine();
		System.out.println("Enter year of birth:");
		int yearOfBirth,yearOfAdmission;
		while (true) { //Check for valid input
			try {
				yearOfBirth = scan.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, please input year of birth again:");
				scan.nextLine();
			}
		}
		
		System.out.println("Enter year of admission:");
		while (true) { //Check for valid input
			try {
				yearOfAdmission = scan.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, please input year of admission again:");
				scan.nextLine();
			}
		}
		
		studentList.put(matricNo,new Student(name,matricNo,yearOfBirth,yearOfAdmission,major));
	}
	
	public static void addCourse() {
		boolean valid = false;  //indicating whether the input professor name is in the record
		
		//Input course information to add to the record
		System.out.println("Enter course name:");
		scan.nextLine();
		String name = scan.nextLine();
		System.out.println("Enter courseCode:");
		String courseCode = scan.nextLine();
		if (courseList.containsKey(courseCode)) {	//Check whether the same course has already be inserted before
			System.out.println("This course may already be inserted before since the same course code is found in the record\n");
			return;
		}
		
		String profName;
		Professor prof = null;
		while (true){	//Ensure the valid input of professor name
			System.out.println("Enter cordinator name:");
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
		
		int courseStructure;
		while (true) {	//Check for valid input
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
		
		
		System.out.println("Enter the number of lectures for this course:");
		int noOfLecs = scan.nextInt();
		System.out.println("Enter the capacity for these lecture:");
		int lecCapacity = scan.nextInt();
		if (courseStructure == 1) {
			courseList.put(courseCode, new Course(name, courseCode, prof, 1));
			courseList.get(courseCode).createLec(noOfLecs, lecCapacity);
		}
		else if (courseStructure == 2){
			System.out.println("Enter the number of tutorials for this course:");
			int noOfTuts = scan.nextInt();
			System.out.println("Enter the capacity for these tutorial:");
			int tutCapacity = scan.nextInt();
			courseList.put(courseCode, new Course(name, courseCode, prof, 2));
			courseList.get(courseCode).createLec(noOfLecs, lecCapacity);
			courseList.get(courseCode).createTut(noOfTuts, tutCapacity);
		}
		else {
			System.out.println("Enter the number of tutorials for this course:");
			int noOfTuts = scan.nextInt();
			System.out.println("Enter the capacity for these tutorial:");
			int tutCapacity = scan.nextInt();
			System.out.println("Enter the number of lab sessions for this course:");
			int noOfLabs = scan.nextInt();
			System.out.println("Enter the capacity for these lab sessions:");
			int labCapacity = scan.nextInt();
			courseList.put(courseCode, new Course(name, courseCode, prof, 3));
			courseList.get(courseCode).createLec(noOfLecs, lecCapacity);
			courseList.get(courseCode).createTut(noOfTuts, tutCapacity);
			courseList.get(courseCode).createLab(noOfLabs, labCapacity);
		}
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
		String matricNo, courseCode;
		Course course;
		Student student;
		//Input the matric number of the corresponding student to register
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
		student = studentList.get(matricNo);
		
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
		course = courseList.get(courseCode);
		
		//Check whether the student has already registered for this course
		if (course.getStudents().contains(student)) {
			System.out.println("This student has successfully registered this course before\n");
			return;
		}
		
		//Check vacancies of the input course and break out the switch statement if there is no vacancies
		if (! course.hasVacancies()) {
			System.out.println("This course has no more vacancies\n");
			return;
		}
		
		/*
		 * The remaining code segment will register student to specific index of course component
		 * based on the course structure
		 */
		
		System.out.printf("Enter the desired lecture index for this course (0-%d):\n", course.getLecs().size()-1);
		while (true) {
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
		if (course.getCourseStructure() == 1)
			return;
		System.out.printf("Enter the desired tutorial index for this course (0-%d):\n", course.getTuts().size()-1);
		while (true) {
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
		if (course.getCourseStructure() == 2)
			return;
		System.out.printf("Enter the desired lab session index for this course (0-%d):\n", course.getLabs().size()-1);
		while (true) {
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
		int choice;
		
		//Choose the class type of the course to check vacancies
		while (true) {
			System.out.println("Choose the type of class that you want to check vacancies:");
			System.out.println("1.Lecture");
			System.out.println("2.Tutorial");
			System.out.println("3.Lab");
			System.out.println("4.Exit");
			while (true) {
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
				System.out.printf("Enter the lecture index you want to check (0-%d):\n", course.getLecs().size()-1);
				int index;
				while (true) {
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
				if (course.getCourseStructure() == 1) {
					System.out.println("This course has no tutorials");
					continue;
				}	
				System.out.printf("Enter the tutorial index you want to check (0-%d):\n", course.getTuts().size()-1);
				int index;
				while (true) {
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
				if (course.getCourseStructure() != 3) {
					System.out.println("This course has no lab sessions\n");
					continue;
				}
				System.out.printf("Enter the lab session index you want to check (0-%d):\n", course.getLabs().size()-1);
				int index;
				while (true) {
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
		while (true) {
			System.out.println("Choose the type of class that you want to print the student list:");
			System.out.println("1.Lecture");
			System.out.println("2.Tutorial");
			System.out.println("3.Lab");
			System.out.println("4.Exit");
			while (true) {
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
				System.out.printf("Enter the lecture index you want to print the student list (0-%d):\n", course.getLecs().size()-1);
				int index;
				while (true) {
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
				if (lecture.getStudents().size() == 0)
					System.out.println("There is no student in this lecture\n");
				else {
					for (Student i: lecture.getStudents()) {
						System.out.println(i);
					}
					System.out.println();
				}
			}else if (choice == 2) {
				if (course.getCourseStructure() == 1) {
					System.out.println("This course has no tutorials\n");
					continue;
				}
				System.out.printf("Enter the tutorial index you want to print the student list (0-%d):\n", course.getTuts().size()-1);
				int index;
				while (true) {
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
				if (tut.getStudents().size() == 0)
					System.out.println("There is no student in this tutorial\n");
				else {
					for (Student i: tut.getStudents()) {
						System.out.println(i);
					}
					System.out.println();
				}
			}else if (choice == 3) {
				if (course.getCourseStructure() != 3) {
					System.out.println("This course has no lab sessions\n");
					continue;
				}
				System.out.printf("Enter the lab index you want to print the student list (0-%d):\n", course.getLabs().size()-1);
				int index;
				while (true) {
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
		
		//Input the course code of the coressponding course to set assessment weight
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
		
		//Input weight for coursework
		System.out.println("Enter the assessment weight of coursework(0-100):");
		double courseWorkWeight;
		while (true) {
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
		
		//Input weight for assignment
		System.out.println("Enter the assignment weight of the coursework:");
		double assignmentWeight;
		while (true) {
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
		
		//Check the whether the student register for the input course
		HashMap<Course, GradeRecord> transcript = student.getTranscript();
		if (! transcript.containsKey(course)) {
			System.out.printf("This student does not register for the course %s\n\n", course);
			return;
		}
		GradeRecord gradeRecord = transcript.get(course);
		
		//Enter grade for assignment
		System.out.printf("Enter the assignment grade (max 100):\n", course.getAssignmentWeight());
		double grade;
		while (true) {
			try {
				grade = scan.nextDouble();
				if (grade<0 || grade>100) {
					System.out.println("Invalid grade, the grade must be between 0 and 100, please input again:");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, please input again:");
				scan.nextLine();
			}
		}
		gradeRecord.setAssigment(grade);
		
		//Enter grade for class-participation
		System.out.printf("Enter the class participation grade (max 100):\n", course.getClassParticipationWeight());
		while (true) {
			try {
				grade = scan.nextDouble();
				if (grade<0 || grade>100) {
					System.out.println("Invalid grade, the grade must be between 0 and 100, please input again:");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, please input again:");
				scan.nextLine();
			}
		}
		gradeRecord.setClassParticipation(grade);
		System.out.println();
	}
	
	public static void enterExamMark() {
		
		//Check whether is any student in the record
		if (studentList.size() == 0) {
			System.out.println("There is no students in the record, please add students first\n");
			return;
		}
		//Check whether is any course in the record
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
		
		//Check whether the student register for the input course
		Course course = courseList.get(courseCode);
		HashMap<Course, GradeRecord> transcript = student.getTranscript();
		if (! transcript.containsKey(course)) {
			System.out.printf("This student does not register for the course %s\n\n", course);
			return;
		}
		GradeRecord gradeRecord = transcript.get(course);
		
		//Enter exam grade
		System.out.println("Enter the exam grade (max 100):");
		double grade;
		while (true) {
			try {
				grade = scan.nextDouble();
				if (grade<0 || grade>100) {
					System.out.println("Invalid grade, the grade must be between 0 and 100, please input again:");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, please input again:");
				scan.nextLine();
			}
		}
		gradeRecord.setExam(grade);
		System.out.println();
	}
	
	public static void printCourseStatistics() {
		if (courseList.size() == 0) {
			System.out.println("There is no courses in the record, please add courses first\n");
			return;
		}
		scan.nextLine();
		System.out.println("Enter the course code:");
		String courseCode;
		while (true) {
			courseCode = scan.nextLine();
			if (! courseList.containsKey(courseCode)) {
				System.out.printf("There is no course whose course code is %s, please input again:\n", courseCode);
				continue;
			}
			break;
		}
		Course course = courseList.get(courseCode);
		System.out.println("Name: "+course.getName()+"    CourseCode: "+course.getCourseCode()+"    Cordinator: Prof "+course.getCordinator());
		System.out.println("Total number of students taken this course: "+course.getStudents().size());
		if (course.getCourseStructure() == 1) {
			System.out.println("This course only has lectures");
			System.out.print("Number of lectures: "+course.getLecs().size()+"     ");
			System.out.println("Lecture capacity: "+course.getLecs().get(0).getCapacity()+"\n");
		} else if(course.getCourseStructure() == 2) {
			System.out.println("This course only has lectures and tutorials");
			System.out.print("Number of lectures: "+course.getLecs().size()+"     ");
			System.out.println("Lecture capacity: "+course.getLecs().get(0).getCapacity());
			System.out.print("Number of tutorials: "+course.getTuts().size()+"     ");
			System.out.println("Tutorial capacity: "+course.getTuts().get(0).getCapacity()+"\n");
		} else {
			System.out.println("This course has lectures, tutorials and lab sessions");
			System.out.print("Number of lectures: "+course.getLecs().size()+"     ");
			System.out.println("Lecture capacity: "+course.getLecs().get(0).getCapacity());
			System.out.print("Number of tutorials: "+course.getTuts().size()+"     ");
			System.out.println("Tutorial capacity: "+course.getTuts().get(0).getCapacity());
			System.out.print("Number of lab sessions: "+course.getLabs().size()+"     ");
			System.out.println("Lab capacity: "+course.getLabs().get(0).getCapacity()+"\n");
		}
	}
	
	public static void printStudentTranscript() {
		if (studentList.size() == 0) {
			System.out.println("There is no student in the record, please add student first:");
			return;
		}
		System.out.println("Enter the student matric number:");
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
		HashMap<Course,GradeRecord> transcript = student.getTranscript();
		for (GradeRecord i: transcript.values()) {
			System.out.println(i);
		}
		System.out.println();
	}
	

}
