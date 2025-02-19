package com.example.cms.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String departmentCode;
}
