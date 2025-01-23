package com.example.cms.controller;

import com.example.cms.controller.exceptions.ClassroomNotFoundException;
import com.example.cms.controller.exceptions.StudentNotFoundException;
import com.example.cms.model.entity.Classroom;
import com.example.cms.model.entity.Student;
import com.example.cms.model.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ClassroomController {

    @Autowired
    private final ClassroomRepository repository;

    // need to init the controller
    public ClassroomController(ClassroomRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/classrooms")
    List<Classroom> retrieveAllClassroom() {
        return repository.findAll();
    }

    @GetMapping("/classrooms/{code}")
    Classroom retrieveClassroom(@PathVariable("code") String code) {
        return repository.findById(code)
                .orElseThrow(() -> new ClassroomNotFoundException(code));
    }

}
