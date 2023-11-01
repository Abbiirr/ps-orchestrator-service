package com.example.orchestrator_service.controller;

import com.example.orchestrator_service.dto.StudentDto;
import com.example.orchestrator_service.entity.Student;
import com.example.orchestrator_service.service.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/data")
    public List<Student> data() {
        return homeService.getAllData();
    }

    @PostMapping("/data")
    public String dataPost(@RequestBody StudentDto studentDto) {
        // Here, you can access the data from the studentDto object
        return homeService.makeData(studentDto);
    }

}
