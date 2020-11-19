package org.example.springboot2test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import starter.HelloworldService;
import starter.SchoolService;

@RestController
@RequestMapping("/helloworld")
public class HelloWorldController {
    @Autowired
    private HelloworldService helloworldService;

    @Autowired
    private SchoolService schoolService;

    @RequestMapping("/sayhello")
    public String sayHello(){
        return helloworldService.sayHello();
    }

    @RequestMapping("/checkschool")
    public String checkSchool() {
        return schoolService.showSchool();
    }
}
