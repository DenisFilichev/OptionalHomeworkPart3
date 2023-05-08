package mainpack;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Student {

    private Long id;
    private String name;
    private List<Byte> grades = new ArrayList<>();
    private SecureRandom random;

    {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Student() {
        id = random.nextLong();
    }

    public Student(String name) {
        this();
        this.name = name;
    }

    public Student(String name, Byte... grades){
        this(name);
        this.grades = Arrays.asList(grades);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Byte> getGrades() {
        return grades;
    }

    public void setGrades(List<Byte> grades) {
        this.grades = grades;
    }

    public void addGrade(byte grade){
        grades.add(grade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id) && name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
