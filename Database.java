import java.io.*;
import java.util.*; 
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.io.File;
import java.util.Scanner;

class Course {

    private String dept;
    private String number;
    private String section;
    private String credit;

    public Course(String dept, String number, String section, String credit) {
        this.dept = dept;
        this.number = number;
        this.section = section;
        this.credit = credit;
    }

    public Course() {
    }

    public String getDept() {
        return this.dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSection() {
        return this.section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getCredit() {
        return this.credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Course)) {
            return false;
        }
        Course course = (Course) o;
        return Objects.equals(dept, course.dept) && Objects.equals(number, course.number)
                && Objects.equals(section, course.section) && Objects.equals(credit, course.credit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dept, number, section, credit);
    }

    @Override
    public String toString() {
        return "{" + " dept='" + getDept() + "'" + ", number='" + getNumber() + "'" + ", section='" + getSection() + "'"
                + ", credit='" + getCredit() + "'" + "}";
    }
} // Course Definition End

class Student {

    private String name;
    private String ID;
    private String age;
    private ArrayList<Course> courses;

    public Student() {
    }

    public Student(String name, String ID, String age, ArrayList<Course> courses) {
        this.name = name;
        this.ID = ID;
        this.age = age;
        this.courses = courses;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public ArrayList<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this){
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(name, student.name) && Objects.equals(ID, student.ID)
        && Objects.equals(age, student.age)
        && Objects.equals(courses, student.courses);
    }

    @Override
    public int hashCode() {
    return Objects.hash(name, ID, age, courses);
    }

    @Override
    public String toString() {
        return "{" + " name='" + getName() + "'" + ", ID='" + getID() + "'" + ", age='" + getAge() + "'" + ", courses='"
                + getCourses() + "'" + "}";
    }
} // Student Definition End

public class Database { // Database Class Definintion Start

    private String DBFilename;
    private LinkedList<String> deletedIDs;
    private String TempDBFilename;
    private File file;
    private File tempFile;
    private Scanner scanF;
    private Scanner scanI;
    private ArrayList<Student> students;
    private int studentCount;
    private BufferedReader reader;
    private BufferedWriter writer;
    private StringBuilder sb;

    public Database() {
        try {
            this.deletedIDs = new LinkedList<String>();
            this.DBFilename = "students.db";
            this.TempDBFilename = "tempstudents.db";
            this.file = new File(DBFilename);
            this.tempFile = new File(TempDBFilename);
            this.scanF = new Scanner(file);
            this.scanI = new Scanner(System.in);
            this.students = new ArrayList<Student>();
            this.studentCount = 0;
            this.reader = new BufferedReader(new FileReader(this.getFile()));
            this.writer = new BufferedWriter(new FileWriter(this.getFile(), true));
            this.sb = new StringBuilder(14);
        } catch (Exception e) {

        }

        File newFile = new File("students.db");
        if (newFile.length() == 0) {

        }

    }

    public Database(String DBFilename, String TempDBFilename, File file, File tempFile, Scanner scan,
            ArrayList<Student> students, int studentCount, BufferedReader reader, BufferedWriter writer,
            StringBuilder sb) {
        try {
            this.DBFilename = DBFilename;
            this.file = file;
            this.tempFile = tempFile;
            this.scanF = new Scanner(file);
            this.scanI = new Scanner(System.in);
            this.students = students;
            this.studentCount = studentCount;
            this.reader = reader;
            this.writer = writer;
            this.sb = sb;
        } catch (Exception e) {

        }
    }

    public void loadToMemoryFromFile() {
        // populate students.db with Arraylist of Students from students.db
        try {
            if (this.getFile().exists()) { // read from file students.db and import into memory
                this.getScanF().nextLine(); // to skip reading 1st line (ID, Name ...)
                while (this.getScanF().hasNextLine()) {
                    String line = this.getScanF().nextLine();

                    String[] values = line.split(",");
                    // int numberOfCourses = (values.length - 4) / 4;

                    String ID = values[0];
                    String name = values[1];
                    String age = values[2];
                    // String numberOfCourses = values[3];
                    ArrayList<Course> courses = new ArrayList<Course>();

                    for (int i = 3; i < values.length - 1; i += 4) {
                        String courseNum = values[i + 1];
                        String dept = values[i + 2];
                        String section = values[i + 3];
                        String credit = values[i + 4];
                        Course course = new Course(dept, courseNum, section, credit);
                        courses.add(course);
                    }

                    Student stud = new Student();
                    stud.setName(name);
                    stud.setAge(age);
                    stud.setCourses(courses);
                    stud.setID(ID);

                    this.getStudents().add(stud);
                    this.setStudentCount(this.getStudentCount() + 1);

                }
            } else {
                // file doesn't exist yet, so create file
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveToFileFromMemory() {
        // save most up-to-date copy of Student Database from memory to file
        
        // check if file exists
        try{
            if(this.getFile().exists()){
                this.setWriter(new BufferedWriter(new FileWriter(this.getFile(), false)));
                String title = "ID,Name,Age,NumberOfCourses,CourseNumber,Department,Section,Credit";
                this.getWriter().write(title);
                this.getWriter().newLine();
                String studentDetails = "";
                for(Student stud: this.getStudents()){
                    studentDetails = "";
                    studentDetails += stud.getID();
                    studentDetails += "," + stud.getName();
                    studentDetails += "," + stud.getAge();
                    studentDetails += "," + stud.getCourses().size();
                    
                    for (Course cours : stud.getCourses()) { 	
                        studentDetails += "," + cours.getNumber();     
                        studentDetails += "," + cours.getDept();
                        studentDetails += "," + cours.getSection();
                        studentDetails += "," + cours.getCredit();
                    }

                    this.getWriter().write(studentDetails);
                    this.getWriter().newLine();
                }
                this.getWriter().close();

            } else {

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String IDGenerator(){
        if(this.getDeletedIDs().peekFirst() != null){
            return this.getDeletedIDs().pop();
        }
        int ID = studentCount + 1;
        return Integer.toString(ID);
    }

    /**
     * @param student: student to insert returns: whether student was inserted
     */
    public boolean add(Student student) {

        if (!students.contains(student)) {
            students.add(student);
            return true;
        }
        return false;
    }

    /**
     * 
     * @param student to remove
     */
    public void remove(Student student) {
        students.remove(student);
    }

    /**
     * removes the student with index i
     * 
     * @param i: index of student to remove
     */
    public void remove(int i) {
        students.remove(i);
    }

    /**
     * returns the student with index i in the database
     * 
     * @param i
     * @return get the student with index i
     */
    public Student getStudent(int i) {
        return students.get(i);
    }

    /**
     * searches for a student in the database
     * 
     * @param student: a student object
     * @return the index at which a student occurs in the database
     */

    public int indexOf(Student student) {
        return students.indexOf(student);
    }

    public boolean contains(Student student) {
        return students.contains(student);
    }

    public String getDBFilename() {
        return this.DBFilename;
    }

    public void setDBFilename(String DBFilename) {
        this.DBFilename = DBFilename;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getTempFile() {
        return this.tempFile;
    }

    public void setTempFile(File tempFile) {
        this.tempFile = tempFile;
    }

    public Scanner getScanF() {
        return this.scanF;
    }

    public void setScanF(Scanner scanF) {
        this.scanF = scanF;
    }

    public Scanner getScanI() {
        return this.scanI;
    }

    public void setScanI(Scanner scanI) {
        this.scanI = scanI;
    }

    public Student getStudentByID(String ID){
        for(Student stud: this.getStudents()){
            if (stud.getID().equals(ID)){
                return stud;
            }
        }
        return null;
    }

    public ArrayList<Student> getListOfStudentByAge(String age){
        ArrayList<Student> studs = new ArrayList<Student>();
        for(Student stud: this.getStudents()){
            if (stud.getAge().equals(age)){
                studs.add(stud);
            }
        }
        return studs;
    }

    public ArrayList<Student> getListOfStudentByName(String name){
        ArrayList<Student> studs = new ArrayList<Student>();
        for(Student stud: this.getStudents()){
            if (stud.getName().toLowerCase().contains(name.toLowerCase())){
                studs.add(stud);
            }
        }
        return studs;
    }

    public ArrayList<Student> getListOfStudentByCourse(String courseG){
        // course = "csc 285"
        ArrayList<Student> studss = new ArrayList<Student>();
        String courset = "";
        for(Student stud: this.getStudents()){
            for(Course crse : stud.getCourses()){
                courset = crse.getDept() + " " + crse.getNumber();
                if (courset.toLowerCase().equals(courseG.toLowerCase())){
                    studss.add(stud);
                    break;
                }
            }
        }
        return studss;
    }

    public LinkedList<String> getDeletedIDs() {
        return this.deletedIDs;
    }

    public void setDeletedIDs(LinkedList<String> deletedIDs) {
        this.deletedIDs = deletedIDs;
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public int getStudentCount() {
        return this.studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public BufferedReader getReader() {
        return this.reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public BufferedWriter getWriter() {
        return this.writer;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public StringBuilder getSb() {
        return this.sb;
    }

    public void setSb(StringBuilder sb) {
        this.sb = sb;
    }

    // Main Application Start
    public static void main(String[] args) {

        Database studentdb = new Database();
        studentdb.loadToMemoryFromFile();

        int command = 0;
        while (command != 7) {
            System.out.println("\n DATABASE OF STUDENTS!");
            System.out.println(" =====================");
            System.out.println(" Choose from the menu of options: ");
            System.out.println(" 1. Print the student details.");
            System.out.println(" 2. Add a student record.");
            System.out.println(" 3. Search for a student.");
            System.out.println(" 4. Delete a student record.");
            System.out.println(" 5. Update a student record.");
            System.out.println(" 6. Save to a file.");
            System.out.println(" 7. Exit the database.");
            System.out.print("\n Enter your option: ");

            Scanner scan = new Scanner(System.in);
            command = scan.nextInt();

            switch (command) {
            case 1:
                System.out.println(studentdb.getStudentCount());
                if (studentdb.getStudentCount() >= 1) {
                    System.out.println("\nALL STUDENT RECORDS:");
                    System.out.println("====================\n");
                    for (Student stud : studentdb.getStudents()) {
                        System.out.println("Name: " + stud.getName());
                        System.out.println("ID: " + stud.getID());
                        System.out.println("Age: " + stud.getAge());
                        System.out.println("Courses: ");
                        for (Course course : stud.getCourses()) {
                            System.out.println("\tDept: " + course.getDept() + ", Course #: " + course.getNumber()
                                    + ", Section " + course.getSection() + ", Credits: " + course.getCredit());
                        }
                    }
                } else {
                    System.out.println("\nALL STUDENT RECORDS:");
                    System.out.println("====================");
                    System.out.println("No students in the database.\n");
                }
                break;

            case 2:
                System.out.println(" Enter the student name: ");
                String name = studentdb.getScanI().next();

                System.out.println(" Enter the student Age: ");
                String age = studentdb.getScanI().next();

                System.out.println(" Enter the number of courses: ");
                int numberOfCourses = studentdb.getScanI().nextInt();

                ArrayList<Course> courses = new ArrayList<Course>();
                Scanner courseScan = new Scanner(System.in).useDelimiter("\\n");

                for(int i = 1; i <= numberOfCourses; i++){
                    System.out.println("Enter the details of course " + i + " in the below format.");
                    System.out.println("<Course department> <course number> <section> <credits>:");
                    String cours = courseScan.nextLine();
                    String[] courseParsed = cours.split(" ");
                    Course course = new Course(courseParsed[0].toUpperCase(), courseParsed[1], courseParsed[2], courseParsed[3]);
                    courses.add(course);
                }           

                Student stud = new Student();
                stud.setName(name);
                stud.setAge(age);
                stud.setCourses(courses);

                stud.setID(studentdb.IDGenerator());

                studentdb.getStudents().add(stud);
                studentdb.setStudentCount(studentdb.getStudentCount() + 1);

                break;
            case 3:
                System.out.println("Search based on ID(1), name(2), age(3), course(4):");
                int option = studentdb.getScanI().nextInt();
                switch(option){
                    case 1:
                        // ID
                        System.out.println("Enter the ID to be searched:");
                        String ID = studentdb.getScanI().next();
                        Student studd = studentdb.getStudentByID(ID);
                        System.out.println();
                        System.out.println("STUDENT WITH ID " + ID + ":");
                        System.out.println("==================");
                        System.out.println("Name: " + studd.getName());
                        System.out.println("ID: " + studd.getID());
                        System.out.println("Age: " + studd.getAge());
                        System.out.println("Courses: ");
                        for (Course course : studd.getCourses()) {
                            System.out.println("\tDept: " + course.getDept() + ", Course #: " + course.getNumber()
                                    + ", Section " + course.getSection() + ", Credits: " + course.getCredit());
                        }
                        System.out.println();
                        break;
                    case 2:
                        // name
                        System.out.print("Enter the name to be searched: ");
                        String naam = studentdb.getScanI().next();
                        
                        ArrayList<Student> studs = studentdb.getListOfStudentByName(naam);

                        System.out.println("\nSTUDENTS WHOSE NAMES MATCH WITH " + naam + " ARE:");
                        System.out.println("==========================================");

                        for(Student stu: studs){
                            System.out.println("Name: " + stu.getName());
                            System.out.println("ID: " + stu.getID());
                            System.out.println("Age: " + stu.getAge());
                            System.out.println("Courses: ");
                            for (Course course : stu.getCourses()) {
                                System.out.println("\tDept: " + course.getDept() + ", Course #: " + course.getNumber()
                                        + ", Section " + course.getSection() + ", Credits: " + course.getCredit());
                            }
                            System.out.println();
                        }
                        break;
                    case 3:
                        // age
                        System.out.print("Enter the age to be searched: ");
                        String aage = studentdb.getScanI().next();
                        ArrayList<Student> studdd = studentdb.getListOfStudentByAge(aage);
                        System.out.println("\nSTUDENTS WITH AGE " + aage + " ARE:");
                        System.out.println("==========================");

                        for(Student stoo: studdd){   
                            System.out.println("Name: " + stoo.getName());
                            System.out.println("ID: " + stoo.getID());
                            System.out.println("Age: " + stoo.getAge());
                            System.out.println("Courses: ");
                            for (Course course : stoo.getCourses()) {
                                System.out.println("\tDept: " + course.getDept() + ", Course #: " + course.getNumber()
                                        + ", Section " + course.getSection() + ", Credits: " + course.getCredit());
                            }
                            System.out.println();
                        }
                        break;
                    case 4:
                        // course
                        System.out.print("Enter the course name to be searched: ");
                        Scanner coloScan = new Scanner(System.in).useDelimiter("\\n");
                        String coors = coloScan.next();
                        
                        ArrayList<Student> studsss = studentdb.getListOfStudentByCourse(coors);

                        System.out.println("\nSTUDENTS WHOSE ARE TAKING " + coors + " COURSES ARE:");
                        System.out.println("==========================================");

                        for(Student stu: studsss){
                            System.out.println("Name: " + stu.getName());
                            System.out.println("ID: " + stu.getID());
                            System.out.println("Age: " + stu.getAge());
                            System.out.println("Courses: ");
                            for (Course course : stu.getCourses()) {
                                System.out.println("\tDept: " + course.getDept() + ", Course #: " + course.getNumber()
                                        + ", Section " + course.getSection() + ", Credits: " + course.getCredit());
                            }
                            System.out.println();
                        }
                        break;
                }
                break;
            case 4:
                System.out.println("Enter the student ID to be removed: ");
                String deleteID = scan.next();
                studentdb.getDeletedIDs().push(deleteID);
                studentdb.getStudents().remove(studentdb.getStudentByID(deleteID));
                studentdb.setStudentCount(studentdb.getStudentCount() - 1);
                break;
            case 5:
                System.out.println("Enter the ID of the student to be updated: ");
                String IID = studentdb.getScanI().next();
                Student chiku = studentdb.getStudentByID(IID);
                System.out.println("Update name(1), year of birth(2), courses(3): ");
                int optionG = studentdb.getScanI().nextInt();
                switch(optionG){
                    case 1:
                        System.out.println("Enter a new name: ");
                        String naame = studentdb.getScanI().next();
                        chiku.setName(naame);
                        System.out.println("Student record has been updated.");
                        break;
                    case 2:
                        System.out.println("Enter a new age: ");
                        String agag = studentdb.getScanI().next();
                        chiku.setAge(agag);
                        System.out.println("Student record has been updated.");
                        break;
                    case 3:
                        System.out.println("Add(1) or Remove(2) a course: ");
                        int optionC = studentdb.getScanI().nextInt();
                        switch(optionC){
                            case 1:
                                System.out.println("Enter the details of the course to be added.");
                                System.out.println("<Course department> <course number> <section> <credits>: ");
                                Scanner addScan = new Scanner(System.in).useDelimiter("\\n");
                                String nuwaCours = addScan.next(); // sth like engl 103 1 3
                                String[] nuwaCoursParsed = nuwaCours.split(" ");
                                Course corswa = new Course(nuwaCoursParsed[0].toUpperCase(), nuwaCoursParsed[1], nuwaCoursParsed[2], nuwaCoursParsed[3]);
                                chiku.getCourses().add(corswa);
                                break;
                            case 2:
                                System.out.println("Enter the details of the course to be removed.");
                                System.out.println("<Course department> <course number> <section> <credits>: ");
                                Scanner addScann = new Scanner(System.in).useDelimiter("\\n");
                                String nuwaCourse = addScann.next(); // sth like engl 103 1 3
                                String[] nuwaCourseParsed = nuwaCourse.split(" ");
                                Course corswaa = new Course(nuwaCourseParsed[0].toUpperCase(), nuwaCourseParsed[1], nuwaCourseParsed[2], nuwaCourseParsed[3]);
                                chiku.getCourses().remove(corswaa);
                                break;
                        }
                        System.out.println("Student record has been updated.");
                        break;
                }
                break;
            case 6:
                System.out.println(" press 6 to save the changes or file\n");
                // System.out.println("Database saved in the file named students.db");
                studentdb.saveToFileFromMemory();
                break;
            default:
                System.out.println("Try typing an option!\n");
                break;
            }

        }
        // User ended program
        System.out.println("GOODBYE.");

    } // Main Application End

} // Database Class Definition End
