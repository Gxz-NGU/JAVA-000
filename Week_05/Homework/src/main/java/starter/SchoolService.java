package starter;

public class SchoolService {
    private Klass klass;

    private Student student;

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String showSchool() {
        return "SchoolService{" +
                "klass=" + klass +
                ", student=" + student +
                '}';
    }
}
