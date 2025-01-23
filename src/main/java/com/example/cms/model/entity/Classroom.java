package com.example.cms.model.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "classrooms")
public class Classroom {

    @Id
    @NotEmpty
    private String code;

    @NotNull
    private int capacity;

    public Classroom(String code, int capacity) {
        this.code = code;
        this.capacity = capacity;
    }
}
