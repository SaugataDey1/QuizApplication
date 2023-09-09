package com.telusko.quizApp.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.telusko.quizApp.Service.QuestionService;
import com.telusko.quizApp.entity.question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = QuestionController.class)
@DisplayName("Testing Question Controller")
public class QuestionControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuestionService service;

    private List<question> questionList;

    private question questionObj;

    @BeforeEach
    void setUp()
    {
        questionObj = new question(1,"test","opt1","opt2","opt3","opt4",
                                         "right", "easy", "programming");
        questionList = new ArrayList<>();
        questionList.add(questionObj);
    }


    @Test
    @DisplayName("Testing Get All Questions endpoint")
    void testGetAllQuestions() throws Exception
    {
        List<question> response = questionList;
        when(service.getAllQuestions()).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/question/GetAllQuestions")).
                 andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].questionTitle", is("test")))
                .andExpect(jsonPath("$.[0].category", is("programming")));

    }

    @Test
    @DisplayName("Testing Get Questions By category endpoint")
    void testGetQuestionByCategory() throws Exception
    {
        List<question> response = questionList;
        when(service.getQuestionsByCategory("programming")).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/question/GetByCategory/programming")).
                andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].questionTitle", is("test")))
                .andExpect(jsonPath("$.[0].category", is("programming")));

    }

    @Test
    @DisplayName("Testing Add Question endpoint")
    void testAddQuestions() throws Exception
    {
        String response = "Successfully Added Question!";
        when(service.addQuestion(questionObj)).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/question/AddQuestion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(questionObj))).
                andDo(print())
                .andExpect(status().isCreated());

    }
}
