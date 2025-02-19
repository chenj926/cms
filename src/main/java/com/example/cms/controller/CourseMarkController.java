package com.example.cms.controller;

import com.example.cms.model.entity.CourseMark;
import com.example.cms.model.repository.CourseMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CourseMarkController {
    @Autowired
    //  try to inject the repository twice, leading to unexpected behavior
    private final CourseMarkRepository repository;

    public CourseMarkController(CourseMarkRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/marks")
    List<CourseMark> retrieveAllMarks() {
        return repository.findAll();
    }

    @PostMapping("/marks/increaseFive/{code}")
    List<CourseMark> increaseFive(@PathVariable("code") String code) {
        repository.increaseFive(code);

        return repository.findAll();
    }
}
