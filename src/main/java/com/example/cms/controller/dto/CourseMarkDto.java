package com.example.cms.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseMarkDto {
    private Long studentId;
    private String courseCode;
    private int mark;
}
