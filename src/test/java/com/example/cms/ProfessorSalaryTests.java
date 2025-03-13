package com.example.cms;

import com.example.cms.model.entity.Professor;
import com.example.cms.model.entity.Student;
import com.example.cms.model.repository.ProfessorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProfessorSalaryTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProfessorRepository professorRepository;

    @Test
    void addProfessorHigherThan30000() throws Exception {
        ObjectNode professorJson = objectMapper.createObjectNode();

        professorJson.put("id", 8888L);
        professorJson.put("firstName", "YesSir");
        professorJson.put("lastName", "Chen");
        professorJson.put("email", "test1@gmail.com");
        professorJson.put("office", "BA101");
        professorJson.put("salary", 65000);

        MockHttpServletResponse response = mockMvc.perform(
                post("/professors").
                        contentType("application/json").
                        content(professorJson.toString())
        ).andReturn().getResponse();

        // assert HTTP code of response is 200 OK
        assertEquals(200, response.getStatus());

        // Assert prof with id 8888 exists in our repository and then get the student object
        assertTrue(professorRepository.findById(8888L).isPresent());
        Professor addedProfessor = professorRepository.findById(8888L).get();

        // Assert the details of the students are correct
        assertEquals(8888L, addedProfessor.getId());
        assertEquals("YesSir", addedProfessor.getFirstName());
        assertEquals("Chen", addedProfessor.getLastName());
        assertEquals("test1@gmail.com", addedProfessor.getEmail());
        assertEquals("BA101", addedProfessor.getOffice());
        assertEquals(65000, addedProfessor.getSalary());

    }

    @Test
    void addProfessorLowerThan30000() throws Exception {
        ObjectNode professorJson = objectMapper.createObjectNode();

        professorJson.put("id", 9999L);
        professorJson.put("firstName", "NoSir");
        professorJson.put("lastName", "Li");
        professorJson.put("email", "test2@gmail.com");
        professorJson.put("office", "BA102");
        professorJson.put("salary", 25000);

        MockHttpServletResponse response = mockMvc.perform(
                post("/professors").
                        contentType("application/json").
                        content(professorJson.toString())
        ).andReturn().getResponse();

        // assert HTTP code of response is 200 OK
        assertEquals(200, response.getStatus());

        // Assert prof with id 8888 exists in our repository and then get the student object
        assertTrue(professorRepository.findById(9999L).isPresent());
        Professor addedProfessor = professorRepository.findById(9999L).get();

        // Assert the details of the students are correct
        assertEquals(9999L, addedProfessor.getId());
        assertEquals("NoSir", addedProfessor.getFirstName());
        assertEquals("Li", addedProfessor.getLastName());
        assertEquals("test2@gmail.com", addedProfessor.getEmail());
        assertEquals("BA102", addedProfessor.getOffice());
        assertEquals(30000, addedProfessor.getSalary());

    }


}
