package starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnClass({SchoolService.class})
@EnableConfigurationProperties(SchoolPropertities.class)
public class SchoolAutoConfiguration {
    @Autowired
    private SchoolPropertities schoolPropertities;

    @Bean
    @ConditionalOnMissingBean(SchoolService.class)
    public SchoolService schoolService() {
        SchoolService schoolService = new SchoolService();
        Klass klass = new Klass();
        List<Student> students = new ArrayList<>();
        for (int id:schoolPropertities.getIds()) {
            for (String name: schoolPropertities.getNames()) {
                students.add(new Student(id,name));
            }
        }
        klass.setStudents(students);

        schoolService.setStudent(new Student(schoolPropertities.getStudentId(),schoolPropertities.getStudentName())) ;
        schoolService.setKlass(klass);
        return schoolService;
    }
}
