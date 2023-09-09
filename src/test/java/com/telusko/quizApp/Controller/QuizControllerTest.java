/*
package com.telusko.quizApp.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telusko.quizApp.Service.QuizService;
import com.telusko.quizApp.entity.QuestionWrapper;
import com.telusko.quizApp.entity.Quiz;
import com.telusko.quizApp.entity.Response;
import com.telusko.quizApp.entity.question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuizController.class)
@DisplayName("Testing Quiz Controller")
public class QuizControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuizService service;

    private QuestionWrapper question_For_User;

    private Quiz quiz;

    @BeforeEach
    void setUp()
    {
        List<question> questions= new ArrayList<>();
        questions.add(new question(1,"test","op1","op2","op3","op4","op1", "test", "test"));
        quiz= new Quiz(1,"Quiz_test",questions);
    }


    @Test
    @DisplayName("Testing Create Quiz endpoint")
    void testCreateQuiz() throws Exception
    {
        ResponseEntity<String> response= new ResponseEntity<>("Created", HttpStatus.CREATED);
        when(service.createQuiz(Mockito.anyString(),Mockito.anyInt(),Mockito.anyString())).thenReturn(response);

        this.mockMvc.perform(post("/quiz/create?category=test&numQ=1&title=test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quiz)))
                        .andDo(print())
                        .andExpect(status().isCreated());

    }



    @Test
    @DisplayName("Testing Get Quiz for User endpoint")
    void getQuizForUser() throws Exception
    {
        QuestionWrapper questionForUser= new QuestionWrapper();
        List<QuestionWrapper> questions = List.of(questionForUser);
        ResponseEntity<List<QuestionWrapper>> response= new ResponseEntity<>(questions,HttpStatus.OK);
        when(service.getQuizQuestions(any())).thenReturn(response);

        this.mockMvc.perform(get("/quiz/get/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    @DisplayName("Testing Get User Response Endpoint")
    void testGetUserResponse() throws Exception
    {
        Response response= new Response(1,"");
        List<Response> responses= List.of(response);
        ResponseEntity<String> responseEntity= new ResponseEntity<>("test",HttpStatus.OK);
        when(service.calculateResult(anyInt(),anyList())).thenReturn(responseEntity);

        this.mockMvc.perform(post("/quiz/submit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responses)))
                        .andDo(print())
                        .andExpect(status().isOk());
    }

}

 */

