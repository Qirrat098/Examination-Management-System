/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_test;


import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ExaminationSystem system = new ExaminationSystem();
    private static final String INSTRUCTORS_FILE = "instructors.txt";
    private static final String STUDENTS_FILE = "students.txt";
    private static final String COURSES_FILE = "courses.txt";
    private static String QUESTION_BANK_FILE = "question_bank.txt";
    private static final String QUIZZES_FILE = "quizzes.txt";
    private static int quiz_attempted = 0;
    
    public static void main(String[] args) {
        loadInitialData();
        //displayMenu();
        displayLoginMenu();
        saveData();
    }

    private static void loadInitialData() {
        loadInstructors();
        loadStudents();
        loadCourses();
        loadQuestionBank();
        loadQuizzes();
    }

    private static void loadInstructors() {
        try (BufferedReader reader = new BufferedReader(new FileReader(INSTRUCTORS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] instructorData = line.split(",");
                String username = instructorData[0];
                String password = instructorData[1];
                String name = instructorData[2];
                system.createInstructor(username, password, name);
            }
        } catch (IOException e) {
            System.out.println("Failed to load instructors: " + e.getMessage());
        }
    }

    private static void loadStudents() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] studentData = line.split(",");
                String username = studentData[0];
                String password = studentData[1];
                String name = studentData[2];
                system.createStudent(username, password, name);
            }
        } catch (IOException e) {
            System.out.println("Failed to load students: " + e.getMessage());
        }
    }
    
    private static void loadCourses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] courseData = line.split(",");
                String courseName = courseData[0];
                String courseCode = courseData[1];
                String instructorUsername = courseData[2];

                Instructor instructor = findInstructorByUsername(instructorUsername);
                if (instructor != null) {
                    system.createCourse(courseName, courseCode, instructor);
                } else {
                    System.out.println("Instructor not found for course: " + courseName);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load courses: " + e.getMessage());
        }
    }

    private static void loadQuestionBank() {
        try (BufferedReader reader = new BufferedReader(new FileReader(QUESTION_BANK_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] questionData = line.split(",");
                String questionText = questionData[0];
                List<String> options = new ArrayList<>();
                for (int i = 1; i <= 4; i++) {
                    options.add(questionData[i]);
                }
                int correctAnswerIndex = Integer.parseInt(questionData[5]);

                Question question = new Question(questionText,QUESTION_BANK_FILE,options, correctAnswerIndex);
                system.addQuestion(question);
            }
        } catch (IOException e) {
            System.out.println("Failed to load question bank: " + e.getMessage());
        }
    }

    private static void loadQuizzes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(QUIZZES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] quizData = line.split(",");
                String quizName = quizData[0];
                String courseCode = quizData[1];
                LocalDateTime dateTime = LocalDateTime.parse(quizData[2]);
                int durationMinutes = Integer.parseInt(quizData[3]);

                Course course = findCourseByCode(courseCode);
                if (course != null) {
                    Quiz quiz = new Quiz(quizName, course, dateTime, durationMinutes);
                    system.createQuiz(quiz);
                } else {
                    System.out.println("Course not found for quiz: " + quizName);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load quizzes: " + e.getMessage());
        }
    }

    private static void saveData() {
        saveInstructors();
        saveStudents();
        saveCourses();
        saveQuestionBank();
        saveQuizzes();
    }

    private static void saveInstructors() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INSTRUCTORS_FILE))) {
            for (Instructor instructor : system.getInstructors()) {
                writer.write(instructor.getUsername() + "," + instructor.getPassword() + "," + instructor.getName());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save instructors: " + e.getMessage());
        }
    }

    private static void saveStudents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student student : system.getStudents()) {
                writer.write(student.getUsername() + "," + student.getPassword() + "," + student.getName());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save students: " + e.getMessage());
        }
    }

    private static void saveCourses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COURSES_FILE))) {
            for (Course course : system.getCourses()) {
                writer.write(course.getCourseName() + "," + course.getCourseCode() + "," + course.getInstructor().getUsername());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save courses: " + e.getMessage());
        }
    }

    private static void saveQuestionBank() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(QUESTION_BANK_FILE))) {
            for (Question question : system.getQuestionBank()) {
                writer.write(question.getQuestionText());
                List<String> options = question.getOptions();
                for (String option : options) {
                    writer.write("," + option);
                }
                writer.write("," + question.getCorrectAnswerIndex());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save question bank: " + e.getMessage());
        }
    }

    private static void saveQuizzes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(QUIZZES_FILE))) {
            for (Quiz quiz : system.getQuizzes()) {
                writer.write(quiz.getQuizName() + "," + quiz.getCourse().getCourseCode() + "," + quiz.getDateTime() +
                        "," + quiz.getDurationMinutes());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save quizzes: " + e.getMessage());
        }
    }

    //private static void displayMenu() 
    //{
        //boolean exit = false;
        //while (!exit) {
           /* System.out.println("=== Examination System Menu ===");
            System.out.println("1. Create Instructor");
            System.out.println("2. Create Student");
            System.out.println("3. Create Course");
            System.out.println("4. Add Question");
            System.out.println("5. Create Quiz");
            System.out.println("6. Start Quiz Attempt");
            System.out.println("7. Submit Quiz");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch(choice) {
                case 1:
                    createInstructor();
                    break;
                case 2:
                    createStudent();
                    break;
                case 3:
                    createCourse();
                    break;
                case 4:
                    //addQuestion(QUESTION_BANK_FILE);
                    break;
                case 5:
                    createQuiz();
                    break;
                case 6:
                    startQuizAttempt();
                    break;
                case 7:
                    submitQuiz();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println();
        }*/
    //}

    private static void createInstructor() {
        System.out.print("Enter instructor username: ");
        String username = scanner.nextLine();

        System.out.print("Enter instructor password: ");
        String password = scanner.nextLine();

        System.out.print("Enter instructor name: ");
        String name = scanner.nextLine();

        system.createInstructor(username, password, name);
        System.out.println("Instructor created successfully.");
    }

    private static void createStudent() {
        System.out.print("Enter student username: ");
        String username = scanner.nextLine();

        System.out.print("Enter student password: ");
        String password = scanner.nextLine();

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        system.createStudent(username, password, name);
        System.out.println("Student created successfully.");
    }

    private static void createCourse() {
        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();

        System.out.print("Enter instructor username for the course: ");
        String instructorUsername = scanner.nextLine();

        Instructor instructor = findInstructorByUsername(instructorUsername);
        if (instructor != null) {
            system.createCourse(courseName, courseCode, instructor);
            System.out.println("Course created successfully.");
        } else {
            System.out.println("Instructor not found. Course creation failed.");
        }
    }
    
     private static void ViewAllCourses() {
        System.out.println("Database Management Theory (DBT - 2001)");
        System.out.println("Database Management Lab (DBL - 2001)");
        System.out.println("Object Oriented Programming Theory (OOPT - 2002)");
        System.out.println("Object Oriented Programming Lab (OOPL - 2002)");
        System.out.println("Operating System Lab (OSL - 2003)");
        System.out.println("Operating System Theory (OST - 2003)");
        System.out.println("Software Design and Architecture (SDA - 2004)");
        
    }

    private static void addQuestion(String filename) {
        char Cadd;
        QUESTION_BANK_FILE = filename;
        boolean exit = false;
        while (!exit) {
            System.out.print("Enter question: ");
            String questionText = scanner.nextLine();
            scanner.nextLine();

            List<String> options = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                System.out.print("Enter option " + (i + 1) + ": ");
                String option = scanner.nextLine();
                
                
                options.add(option);
            }

            System.out.print("Enter the index of the correct answer: ");
            int correctAnswerIndex = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Question question = new Question(questionText,QUESTION_BANK_FILE, options, correctAnswerIndex);
            system.addQuestion(question);
            System.out.println("Question added successfully.");
            
            System.out.println("Do you want to add another question? Press Y for Yes or N for No");
            Cadd = scanner.next().charAt(0);
            
            if (Cadd == 'Y' || Cadd == 'y') {
            exit = false;
        } else {
            exit = true;
            }    
            
            
        }
        
    }
    public static void chooseCourse(){
        String filename;
        boolean exit = false;
        while (!exit) {
        System.out.println("Which Course you want your questions to add?");
        System.out.println("Press: ");
        System.out.println("1: Database Management Theory (DBT - 2001)");
        System.out.println("2: Database Management Lab (DBL - 2001)");
        System.out.println("3: Object Oriented Programming Theory (OOPT - 2002)");
        System.out.println("4: Object Oriented Programming Lab (OOPL - 2002)");
        System.out.println("5: Operating System Lab (OSL - 2003)");
        System.out.println("6: Operating System Theory (OST - 2003)");
        System.out.println("7: Software Design and Architecture (SDA - 2004)");
        System.out.println("0: Exit Application ");
        System.out.println("Enter choice: ");


        
        int choose = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch(choose) {
                
                case 1:
                    filename = "DMSTheory.txt";
                    addQuestion(filename);
                    break;
                case 2:
                    filename = "DBMLab.txt";
                    addQuestion(filename);
                    break;
                case 3:
                    filename = "OOPTheory.txt";
                    addQuestion(filename);
                    break;
                case 4:
                    filename = "OOPLab.txt";
                    addQuestion(filename);
                    break;
                case 5:
                    filename = "OSTheory.txt";
                    addQuestion(filename);
                    break;
                case 6:
                    filename = "OSLab.txt";
                    addQuestion(filename);
                    break;
                case 7:
                    filename = "SDA.txt";
                    addQuestion(filename);
                    break;    
                case 0:
                     exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        
    }
    }

    private static void createQuiz() {
        System.out.print("Enter quiz name: ");
        String quizName = scanner.nextLine();

        System.out.print("Enter course code for the quiz: ");
        String courseCode = scanner.nextLine();

        Course course = findCourseByCode(courseCode);
        if (course != null) {
            LocalDateTime dateTime = LocalDateTime.now();
            System.out.println("Quiz creation date/time: " + dateTime);

            System.out.print("Enter quiz duration in minutes: ");
            int durationMinutes = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Quiz quiz = new Quiz(quizName, course, dateTime, durationMinutes);
            system.createQuiz(quiz);
            System.out.println("Quiz created successfully.");
        } else {
            System.out.println("Course not found. Quiz creation failed.");
        }
    }
    
    public static int getMatchingCount(List<String> list1, List<String> list2) {
        int count = 0;
        int minLength = Math.min(list1.size(), list2.size());

        for (int i = 0; i < minLength; i++) {
            if (list1.get(i).equals(list2.get(i))) {
                count++;
            }
        }

        return count;
    }

    public static void writeListToFile(List<String> dataList, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String item : dataList) {
                writer.write(item);
                writer.newLine(); // Add a new line after each item (optional)
            }
         //   System.out.println("List has been written to the file: " + fileName);
        } catch (IOException e) {
          //  System.err.println("Error writing the file: " + e.getMessage());
        }
    }
    private static void startQuizAttempt() {
        System.out.print("Enter student username: ");
        String studentUsername = scanner.nextLine();
        
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();

        System.out.print("Enter quiz name: ");
                
        String quizName = scanner.nextLine();

        
        String answer_sheet_path = courseCode+"answered_sheet.txt";
        List<String> answered_sheet = new ArrayList<>();
        List<String> correct_answered_sheet = new ArrayList<>();
        Student student = findStudentByUsername(studentUsername);
        List<String> course_quiz = findQuizByCodeShow(courseCode);
        Course course = findCourseByCode(courseCode);
        
        String listAsString = course_quiz.toString();
        System.out.println("*****************************  QUIZ Started *****************************");
        
        
        for (String quiz : course_quiz) {
            // Perform operations using the current number
            //System.out.println(quiz);
            // Split the string using a comma (",") as the delimiter
            String[] parts = quiz.split(",");

            if (parts.length >= 3) {
                System.out.println(parts[0].trim());
                System.out.println(parts[1].trim());
                System.out.println(parts[2].trim());
                System.out.println(parts[3].trim());
                System.out.println(parts[4].trim());
                
                    
                correct_answered_sheet.add(parts[5].trim());

               
            }
            System.out.println("Write the answer of the question as 1,2,3,4:  ");
            String answertId = scanner.nextLine();
            System.out.println("");
                   
            answered_sheet.add(answertId);
        }
        
        
        
        quiz_attempted = quiz_attempted + 1;
        System.out.println("*****************************  QUIZ Finished *****************************");
        System.out.println("");
                
        System.out.println("Your attempted Quizes:  "+quiz_attempted);
        
        if (student != null && course_quiz != null) {
            
            System.out.println("Correct answers:  "+correct_answered_sheet);
            System.out.println("Your answeres:  "+answered_sheet);
            int matchingCount = getMatchingCount(answered_sheet, correct_answered_sheet);
            System.out.println("Number of correct answers are: " + matchingCount);
            
          //  QuizAttempt quizAttempt = system.startQuizAttempt(student, course, listAsString);
          //  if (quizAttempt != null) {
                System.out.println("");
                System.out.println("-------------------------------------------------------------");
                System.out.println("");
                
                System.out.println("Quiz attempt started successfully.");
          //      System.out.println("Quiz Attempt ID: " + quizAttempt.getAttemptId());
                System.out.println("Quiz: " + quizName);
                System.out.println("Course Code: " + courseCode);
                System.out.println("Your Score: " + matchingCount);
         //       System.out.println("Start Time: " + quizAttempt.getStartTime());
     /*       } else {
                System.out.println("Quiz attempt limit exceeded. Cannot start quiz attempt.");
            }*/
            writeListToFile(correct_answered_sheet, answer_sheet_path);
            System.out.println("");
            System.out.println("------------------------------------------------------------------");
            System.out.println("");
        } else {
            System.out.println("Invalid student, course, or quiz. Quiz attempt failed.");
        }
    }

    private static void submitQuiz() {
        System.out.print("Enter quiz attempt ID: ");
        int attemptId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
/*
        QuizAttempt quizAttempt = system.getQuizAttemptById(attemptId);
        if (quizAttempt != null) {
            List<Question> questions = quizAttempt.getQuiz().getQuestions();
            for (Question question : questions) {
                System.out.println(question.getQuestionText());
                List<String> options = question.getOptions();
                for (int i = 0; i < options.size(); i++) {
                    System.out.println((i + 1) + ". " + options.get(i));
                }
                System.out.print("Enter your answer (option number): ");
                int answerIndex = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                quizAttempt.submitAnswer(question, answerIndex);
            }

            quizAttempt.submitQuiz();
            System.out.println("Quiz submitted successfully.");
            System.out.println("Score: " + quizAttempt.getScore());
        } else {
            System.out.println("Quiz attempt not found. Quiz submission failed.");
        } 
 */
}



    private static Instructor findInstructorByUsername(String username) {
        for (Instructor instructor : system.getInstructors()) {
            if (instructor.getUsername().equals(username)) {
                return instructor;
            }
        }
        return null;
    }

    private static Student findStudentByUsername(String username) {
        for (Student student : system.getStudents()) {
            if (student.getUsername().equals(username)) {
                return student;
            }
        }
        return null;
    }

    private static Course findCourseByCode(String courseCode) {
        for (Course course : system.getCourses()) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
    
    private static List<String> findQuizByCodeShow(String courseCode) {
        String filename = courseCode+".txt";
        Course course = findCourseByCode(courseCode);
        String fileContentall = "";
        List<String> fileContent = new ArrayList<>();

        if (course != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                fileContent = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    fileContent.add(line);
                }
                fileContentall = fileContentall + fileContent;
            /*    System.out.println("File Content:");
                for (String content : fileContent) {
                    System.out.println(content);
                }*/
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            }
        } else {
            System.out.println("Course not found.");
        }
        
        return fileContent;
    }

    
    private static Instructor loginAsInstructor() {
    System.out.print("Enter instructor username: ");
    String username = scanner.nextLine();

    System.out.print("Enter instructor password: ");
    String password = scanner.nextLine();

    try (Scanner fileScanner = new Scanner(new File("instructors.txt"))) {
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split(",");
            if (parts.length == 3) {
                String fileUsername = parts[0].trim();
                String filePassword = parts[1].trim();
                String instructorName = parts[2].trim();

                if (username.equals(fileUsername) && password.equals(filePassword)) {
                    return new Instructor(instructorName, username, password);
                }
            }
        }
    } catch (FileNotFoundException e) {
        System.out.println("Error: File not found");
    }

    return null;
}

private static Student loginAsStudent() {
    System.out.print("Enter student username: ");
    String username = scanner.nextLine();

    System.out.print("Enter student password: ");
    String password = scanner.nextLine();

    try (Scanner fileScanner = new Scanner(new File("students.txt"))) {
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split(",");
            if (parts.length == 3) {
                String fileUsername = parts[0].trim();
                String filePassword = parts[1].trim();
                String studentName = parts[2].trim();

                if (username.equals(fileUsername) && password.equals(filePassword)) {
                    return new Student(studentName, username, password);
                }
            }
        }
    } catch (FileNotFoundException e) {
        System.out.println("Error: File not found");
    }

    return null;
}

private static void instructorMenu(Instructor instructor) {
    // TODO: Implement instructor menu options
    System.out.println("Logged in as instructor! ");
    
     boolean exit = false;
        while (!exit) {
            System.out.println("=== Examination System Menu ===");
            System.out.println("1. View All Courses");
            System.out.println("2. Go to Quiz Section");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch(choice) {
                
                case 1:
                    ViewAllCourses();
                    break;
                case 2:
                    go_to_Quiz_Menu();
                    //createQuiz();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println();
        }
}

private static void go_to_Quiz_Menu() {
    // TODO: Implement instructor menu options
    //System.out.println("Logged in as instructor: " + instructor.getName());
    
     boolean exit = false;
        while (!exit) {
            System.out.println("=== Quiz System Menu ===");
          
            System.out.println("1. View All Quizes");
            System.out.println("2. Add Question to Existing Quizes");
            System.out.println("3. Create new Quiz");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch(choice) {
                
                case 1:
                    ViewAllQuizes();
                    break;
                case 2:
                    chooseCourse();
                    break;
                case 3:
                    createQuiz();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
}
private static void ViewAllQuizes(){
       try {
            // Specify the path to your file
            File file = new File("quizzes.txt");

            // Create a Scanner to read the file
            Scanner scanner = new Scanner(file);

            // Read and display each line from the file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }

            // Close the scanner
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
}
private static void studentMenu(Student student) {
    // TODO: Implement student menu options
    System.out.println("Logged in as student! ");
    
     boolean exit = false;
        while (!exit) {
            System.out.println("=== Examination System Menu ===");
            System.out.println("1. View All Courses");
            System.out.println("2. Go to Quiz Section");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch(choice) {
                
                case 1:
                    ViewAllCourses();
                    break;
                case 2:
                    go_to_Student_Quiz_Menu();
                    //createQuiz();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println();
        }
    
}
private static void go_to_Student_Quiz_Menu() {
    // TODO: Implement instructor menu options
    //System.out.println("Logged in as instructor: " + instructor.getName());
    
     boolean exit = false;
        while (!exit) {
            System.out.println("=== Quiz System Menu ===");
          
            System.out.println("1. Attempt Quiz");
             System.out.println("2. View all available quizes");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch(choice) {
                
                case 1:
                    startQuizAttempt();
                    break;
                case 2:
                    ViewAllQuizes();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
}


private static void displayLoginMenu() 
{
    boolean exit = false;
    while (!exit) {
        System.out.println("=== Examination System Menu ===");
        System.out.println("1. Login as Instructor");
        System.out.println("2. Login as Student");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                Instructor instructor = loginAsInstructor();
                if (instructor != null) {
                    instructorMenu(instructor);
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                }
                break;
            case 2:
                Student student = loginAsStudent();
                if (student != null) {
                    studentMenu(student);
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                }
                break;
            case 0:
                exit = true;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }

        System.out.println();
    }
}
}
