package javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class Config {
    @Bean("student111")
    public Student getStudent() {
        return new Student(1,"student111");
    }
}
