package com.telusko.quizApp.Service;

import com.telusko.quizApp.Exception.ResourceNotFoundException;
import com.telusko.quizApp.dao.QuestionDao;
import com.telusko.quizApp.entity.question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("QuestionServiceTest")
public class QuestionServiceTest
{
    @Mock
    private QuestionDao questionRepository;

    @InjectMocks
    private QuestionService questionService;

    private question questionObj;

    private List<question> questions;

    private String category;

    @BeforeEach
    public void setUp()
    {
        questionObj = new question(1, "Who is the creator of Java?", "James Gosling",
                                   "Linus Torvalds", "G V Rossum", "None",
                                   "James Gosling", "Programming", "easy");

        questions = new ArrayList<>();

        category = "Programming";
    }

    @Test
    @DisplayName("Testing Get All Questions - Success scenario")
    void testGetAllQuestionsSuccessScenario()
    {
        questions.add(questionObj);
        when(questionRepository.findAll()).thenReturn(questions);

        Assertions.assertEquals(questions, questionService.getAllQuestions());
    }

    @Test
    @DisplayName("Testing Add Questions")
    void testAddQuestions()
    {
        String response = "success";
        Assertions.assertEquals(response, questionService.addQuestion(questionObj));
        verify(questionRepository).save(questionObj);
    }

    @Test
    @DisplayName("Testing Get Question By category")
    void testGetQuestionByCategory()
    {
        questions.add(questionObj);
        List<question> responses = questions;
        when(questionRepository.findByCategory(category)).thenReturn(questions);
        Assertions.assertEquals(responses, questionService.getQuestionsByCategory(category));
    }

    @Test
    @DisplayName("Testing Get Question By category: Exception Scenario")
    void testGetQuestionByCategoryException()
    {
        when(questionRepository.findByCategory(category)).thenReturn(List.of());
        Assertions.assertThrows(ResourceNotFoundException.class,()->questionService.getQuestionsByCategory(category));
    }

    @Test
    @DisplayName("Testing Get All Questions- Exception scenario")
    void testGetAllQuestionsExceptionScenario()
    {
        when(questionRepository.findAll()).thenReturn(List.of());
        Assertions.assertThrows(ResourceNotFoundException.class,()->questionService.getAllQuestions());
    }
}
