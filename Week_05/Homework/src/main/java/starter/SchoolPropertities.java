package starter;

import java.util.ArrayList;
import java.util.List;

public class SchoolPropertities {

    private static final Klass defaultKlass = new Klass();
    static {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1,"Bob"));
        students.add(new Student(2,"Alice"));
        students.add(new Student(3,"Peter"));
        defaultKlass.setStudents(students);
    }

    private Klass klass;
    private Student student;

}

