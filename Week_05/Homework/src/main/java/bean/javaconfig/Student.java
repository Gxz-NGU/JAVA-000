package bean.javaconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Component
public class Student {
    private int id;
    private String name;
    public Student() {
        System.out.println("Construct Student");
    }

}
