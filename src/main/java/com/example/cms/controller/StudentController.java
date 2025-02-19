package com.example.cms.controller;

import com.example.cms.controller.dto.StudentDto;
import com.example.cms.controller.exceptions.DepartmentNotFoundException;
import com.example.cms.controller.exceptions.StudentNotFoundException;
import com.example.cms.model.entity.Department;
import com.example.cms.model.entity.Student;
import com.example.cms.model.repository.DepartmentRepository;
import com.example.cms.model.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class StudentController {
    @Autowired
    private final StudentRepository repository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/students")
    List<Student> retrieveAllStudents() {
        return repository.findAll();
    }

    @PostMapping("/students")
    Student createStudent(@RequestBody StudentDto studentDto) {
        Student newStudent = new Student();
        newStudent.setId(studentDto.getId());
        newStudent.setFirstName(studentDto.getFirstName());
        newStudent.setLastName(studentDto.getLastName());
        newStudent.setEmail(studentDto.getEmail());

        Department department = departmentRepository.findById(studentDto.getDepartmentCode()).orElseThrow(
                () -> new DepartmentNotFoundException(studentDto.getDepartmentCode())
        );
        newStudent.setDepartment(department);
        return repository.save(newStudent);
    }

    @GetMapping("/students/{id}")
    Student retrieveStudent(@PathVariable("id") Long studentId) {
        return repository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
    }

    @PutMapping("/students/{id}")
    Student updateStudent(@RequestBody StudentDto studentDto, @PathVariable("id") Long studentId) {
        return repository.findById(studentId)
                .map(student -> {
                    student.setFirstName(studentDto.getFirstName());
                    student.setLastName(studentDto.getLastName());
                    student.setEmail(studentDto.getEmail());
                    Department department = departmentRepository.findById(studentDto.getDepartmentCode()).orElseThrow(
                            () -> new DepartmentNotFoundException(studentDto.getDepartmentCode())
                    );
                    student.setDepartment(department);
                    return repository.save(student);
                })
                .orElseGet(() -> {
                    Student newStudent = new Student(); // set new student
                    newStudent.setId(studentId);
                    newStudent.setFirstName(studentDto.getFirstName());
                    newStudent.setLastName(studentDto.getLastName());
                    newStudent.setEmail(studentDto.getEmail());

                    Department department = departmentRepository.findById(studentDto.getDepartmentCode()).orElseThrow(
                            () -> new DepartmentNotFoundException(studentDto.getDepartmentCode())
                    );
                    newStudent.setDepartment(department);
                    return repository.save(newStudent);
                });
    }

    @DeleteMapping("/students/{id}")
    void deleteStudent(@PathVariable("id") Long studentId) {
        repository.deleteById(studentId);
    }

    @GetMapping("/students/search/{searchstring}")
    List<Student> searchStudent(@PathVariable("searchstring") String searchString) {
        return repository.search(searchString);
    }

    @GetMapping("/students/top")
    List<Student> retrieveTopStudents() {
        return repository.findTopStudents();
    }
}
