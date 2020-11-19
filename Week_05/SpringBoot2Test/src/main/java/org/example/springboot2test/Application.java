package org.example.springboot2test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@ServletComponentScan(value = "org.example.springboot2test.listener")
public class Application {
    @RequestMapping("/")
    String index() {
        return "Hello";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
