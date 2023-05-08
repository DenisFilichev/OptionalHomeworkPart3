package mainpack;

public class StudentList {
    private final static StudentList instance = new StudentList();
    private final StudentService studentService = new StudentServiceImpl();

    private StudentList() {
    }

    public void addStudent(String studentName, Byte...grades) {
        studentService.addStudent(new Student(studentName, grades));
    }

    public void changeStudent(Student student){
        studentService.changeStudent(student);
    }

    public void remove(int index) {
        Student student = studentService.getAllStudents().get(index);
        studentService.removeStudent(student);
    }

    public int getStudentCount() {
        return studentService.getAllStudents().size();
    }

    public Student getStudent(int index) {
        return studentService.getAllStudents().get(index);
    }

    public static StudentList getInstance() {
        return instance;
    }
}
