package com.example.cms.controller;

import com.example.cms.controller.dto.CourseDto;
import com.example.cms.controller.dto.CourseMarkDto;
import com.example.cms.controller.exceptions.CourseNotFoundException;
import com.example.cms.controller.exceptions.ProfessorNotFoundException;
import com.example.cms.controller.exceptions.StudentNotFoundException;
import com.example.cms.model.entity.*;
import com.example.cms.model.repository.CourseMarkRepository;
import com.example.cms.model.repository.CourseRepository;
import com.example.cms.model.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CourseMarkController {
    @Autowired
    //  try to inject the repository twice, leading to unexpected behavior
    private final CourseMarkRepository repository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

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

    @PostMapping("/marks")
    CourseMark createMark(@RequestBody CourseMarkDto courseMarkDto) {
        CourseMark mark = new CourseMark();

        // set mark id
        mark.setMarkId(new CourseMarkKey(courseMarkDto.getStudentId(), courseMarkDto.getCourseCode()));

        // get id, add and check student and course
        Student student = studentRepository.findById(courseMarkDto.getStudentId()).orElseThrow(
                () -> new StudentNotFoundException(courseMarkDto.getStudentId()));
        mark.setStudent(student);

        Course course = courseRepository.findById(courseMarkDto.getCourseCode()).orElseThrow(
                () -> new CourseNotFoundException(courseMarkDto.getCourseCode()));
        mark.setCourse(course);

        mark.setMark(courseMarkDto.getMark());

        return repository.save(mark);
    }

    @PutMapping("/marks/{studentId}/{courseCode}")
    CourseMark updateMark(@RequestBody CourseMarkDto courseMarkDto,
                          @PathVariable("studentId") Long studentId,
                          @PathVariable("courseCode") String courseCode) {
        return repository.findById(new CourseMarkKey(studentId, courseCode))
                .map(courseMark -> {
                    courseMark.setMarkId(new CourseMarkKey(courseMarkDto.getStudentId(), courseMarkDto.getCourseCode()));
                    courseMark.setStudent(studentRepository.findById(courseMarkDto.getStudentId()).orElseThrow(
                            () -> new StudentNotFoundException(courseMarkDto.getStudentId())));
                    courseMark.setCourse(courseRepository.findById(courseMarkDto.getCourseCode()).orElseThrow(
                            () -> new CourseNotFoundException(courseMarkDto.getCourseCode())));

                    courseMark.setMark(courseMarkDto.getMark());
                    return repository.save(courseMark);

                })
                .orElseGet(() -> {
                    CourseMark courseMark = new CourseMark();
                    // set mark id
                    courseMark.setMarkId(new CourseMarkKey(courseMarkDto.getStudentId(), courseMarkDto.getCourseCode()));

                    // get id, add and check student and course
                    Student student = studentRepository.findById(courseMarkDto.getStudentId()).orElseThrow(
                            () -> new StudentNotFoundException(courseMarkDto.getStudentId()));
                    courseMark.setStudent(student);

                    Course course = courseRepository.findById(courseMarkDto.getCourseCode()).orElseThrow(
                            () -> new CourseNotFoundException(courseMarkDto.getCourseCode()));
                    courseMark.setCourse(course);

                    courseMark.setMark(courseMarkDto.getMark());

                    return repository.save(courseMark);
                });
    }


    @DeleteMapping("/marks/{studentId}/{courseCode}")
    void deleteMark(@PathVariable("studentId") Long studentId,
                          @PathVariable("courseCode") String courseCode) {
        repository.deleteById(new CourseMarkKey(studentId, courseCode));
    }

}
