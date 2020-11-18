package starter;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;


@Data
public class School {
    
    // Resource 
    @Autowired(required = true) //primary
    Klass class1;
    
    Student student100;

    
}
