package mainpack;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();
    void addStudent(Student student);
    void changeStudent(Student student);
    void removeStudent(Student student);
}
