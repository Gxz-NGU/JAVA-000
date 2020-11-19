package starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
@ConfigurationProperties(prefix = "school")
public class SchoolPropertities {
    private static final List<Integer> DEFAULT_IDS = new ArrayList(3);
    private static final List<String> DEFAULT_NAMES = new ArrayList(3);

    private List<Integer> ids = DEFAULT_IDS;

    private List<String> names = DEFAULT_NAMES;

    private int studentId = 111;

    private String studentName = "student111";

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

}

