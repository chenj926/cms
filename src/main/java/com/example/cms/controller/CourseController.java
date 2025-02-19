package com.example.cms.controller;

import com.example.cms.controller.dto.CourseDto;
import com.example.cms.controller.exceptions.ClassroomNotFoundException;
import com.example.cms.controller.exceptions.CourseNotFoundException;
import com.example.cms.controller.exceptions.ProfessorNotFoundException;
import com.example.cms.model.entity.Classroom;
import com.example.cms.model.entity.Course;
import com.example.cms.model.entity.Professor;
import com.example.cms.model.repository.ClassroomRepository;
import com.example.cms.model.repository.CourseRepository;
import com.example.cms.model.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class CourseController {
    @Autowired
    private final CourseRepository repository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    public CourseController(CourseRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/courses")
    List<Course> retrieveAllCourses() {
        return repository.findAll();
    }

    @PostMapping("/courses")
    Course createCourse(@RequestBody CourseDto courseDto) {
        Course newCourse = new Course();
        newCourse.setName(courseDto.getName());
        newCourse.setCode(courseDto.getCode());

        // get id, add and check prof
        Professor professor = professorRepository.findById(courseDto.getProfessorId()).orElseThrow(
                () -> new ProfessorNotFoundException(courseDto.getProfessorId()));
        newCourse.setProfessor(professor);
        // get id, add and check classroom
//        Classroom classroom = classroomRepository.findById(courseDto.getClassroomCode()).orElseThrow(
//                () -> new ClassroomNotFoundException(courseDto.getClassroomCode()));
//        newCourse.setClassroom(classroom);

        return repository.save(newCourse);
    }

    @GetMapping("/courses/{code}")
    Course retrieveCourse(@PathVariable("code") String courseCode) {
        return repository.findById(courseCode)
                .orElseThrow(() -> new CourseNotFoundException(courseCode));
    }

    @PutMapping("/courses/{code}")
    Course updateCourse(@RequestBody CourseDto courseDto, @PathVariable("code") String courseCode) {
        return repository.findById(courseCode)  // Returns Optional<Course>
                .map(course -> {                // If present, transforms the course
                    course.setName(courseDto.getName());
                    Professor professor = professorRepository.findById(courseDto.getProfessorId()).orElseThrow(
                            () -> new ProfessorNotFoundException(courseDto.getProfessorId()));
                    course.setProfessor(professor);
                    return repository.save(course);
                })
                .orElseGet(() -> {              // If not present, create a new course
                    Course newCourse = new Course();
                    newCourse.setCode(courseCode);
                    Professor professor = professorRepository.findById(courseDto.getProfessorId()).orElseThrow(
                            () -> new ProfessorNotFoundException(courseDto.getProfessorId()));
                    newCourse.setProfessor(professor);
                    return repository.save(newCourse);
                });
    }

    @DeleteMapping("/courses/{code}")
    void deleteCourse(@PathVariable("code") String courseCode) {
        repository.deleteById(courseCode);
    }
}