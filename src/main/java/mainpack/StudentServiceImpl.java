package mainpack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentServiceImpl implements StudentService{

    private final FilesWorkUtils filesWorkUtils = new FilesWorkUtils();

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try {
            students = filesWorkUtils.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void addStudent(Student student) {
        try {
            List<Student> students = getAllStudents();
            students.add(student);
            filesWorkUtils.write(new HashSet<>(students));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeStudent(Student student) {
        System.out.println(student.getGrades());
        try {
            Set<Student> students = new HashSet<>(getAllStudents());
            students.remove(student);
            students.add(student);
            filesWorkUtils.write(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeStudent(Student student) {
        try {
            List<Student> students = getAllStudents();
            students.remove(student);
            filesWorkUtils.write(new HashSet<>(students));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
