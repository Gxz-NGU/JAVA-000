
package bean.javaconfig;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestJavaConfig {

    @Test
    public void test() {
        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(Config.class);
        Student student =(Student)applicationContext.getBean("student111");
        System.out.println(student);
    }
}
