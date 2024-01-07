package com.telusko.quizApp.Service;

import com.telusko.quizApp.Exception.ResourceNotFoundException;
import com.telusko.quizApp.dao.QuestionDao;
import com.telusko.quizApp.dao.QuizDao;
import com.telusko.quizApp.entity.QuestionWrapper;
import com.telusko.quizApp.entity.Quiz;
import com.telusko.quizApp.entity.Response;
import com.telusko.quizApp.entity.question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test for QuizService")
public class QuizServiceTest
{
    @Mock
    private QuizDao quizRepository;

    @Mock
    private QuestionDao questionRepository;

    @InjectMocks
    private QuizService quizService;

    private List<question> quizQuestions;

    private Quiz quiz;


    @BeforeEach
    public void setUp() {

        question questionObj = new question(1,"Who is the creator of Java?","James Gosling",
                "Linus Torvalds","G V Rossum","None","James Gosling",
                "Programming","easy");

        quizQuestions = new ArrayList<>();

        quizQuestions.add(questionObj);

    }

    @Test
    @DisplayName("Testing Create Quiz")
    void testCreateQuiz() {

        Mockito.when(questionRepository.findRandomQuestionByCategory("Programming", 1)).thenReturn(quizQuestions);

        quiz= new Quiz();
        quiz.setTitle("quiz1");
        quiz.setQuestions(quizQuestions);

        ResponseEntity<String> response= new ResponseEntity<>("Created Quiz!", HttpStatus.CREATED);

        Assertions.assertEquals(response,quizService.createQuiz("Programming", 1,"quiz1"));

        // If the Return Type of the method is void then we have to use -> Mockito.verify(), because Assertions.assertEquals() will show Compile Time Error.
        Mockito.verify(quizRepository).save(quiz);  // verifies that -> QuizRepository save(quiz) -> this method runs or not.



    }

    @Test
    @DisplayName("Testing Get Questions for User")
    void testGetQuestionsForUser()
    {
        quiz = new Quiz(1, "quiz2", quizQuestions);
        Mockito.when(quizRepository.findById(1)).thenReturn(Optional.ofNullable(quiz));

        List<QuestionWrapper> userQuestions= quizService.getQuizQuestions(1).getBody();
        Assertions.assertNotNull(userQuestions);
        Assertions.assertEquals(1,userQuestions.size());

    }

    @Test
    @DisplayName("Testing Calculate Result method")
    void testCalculateResult()
    {
        quiz = new Quiz(1,"quiz3", quizQuestions);
        Mockito.when(quizRepository.findById(1)).thenReturn(Optional.ofNullable(quiz));

        Response response = new Response(1,"James Gosling");
        List<Response> responses = new ArrayList<>();
        responses.add(response);

        ResponseEntity<Integer> responseForUser= quizService.calculateResult(1, responses);
       // Assertions.assertTrue(Objects.requireNonNull(responseForUser.getBody()).contains(1));
        Assertions.assertNotNull(responseForUser);
    }

    @Test
    @DisplayName("Testing Get Questions for User : Exception Scenario")
    void testGetQuestionsForUserException()
    {
        Mockito.when(quizRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, ()->quizService.getQuizQuestions(1));
    }

    @Test
    @DisplayName("Testing Calculate Result : No Quiz Found Exception Scenario")
    void testCalculateResultNoQuizFound()
    {
        Mockito.when(quizRepository.findById(1)).thenReturn(Optional.empty());
        List<Response> responses= new ArrayList<>();
        Assertions.assertThrows(ResourceNotFoundException.class, ()->quizService.calculateResult(1,responses));
    }

    @Test
    @DisplayName("Testing Calculate Result: Empty Response Exception Scenario")
    void testCalculateResultEmptyResponse()
    {
        quiz= new Quiz(1,"quiz3",quizQuestions);
        Mockito.when(quizRepository.findById(1)).thenReturn(Optional.ofNullable(quiz));
        Response response= new Response(1,null);
        List<Response> responses= Collections.singletonList(response);
        Assertions.assertThrows(ResourceNotFoundException.class, ()->quizService.calculateResult(1,responses));
    }

    @Test
    @DisplayName("Create Quiz : Exception Scenario")
    void testCreateQuizException()
    {
        quizQuestions= new ArrayList<>();
        Mockito.when(questionRepository.findRandomQuestionByCategory("Music",1)).thenReturn(quizQuestions);
        Assertions.assertThrows(ResourceNotFoundException.class, ()->quizService.createQuiz("Music",1,"quiz2"));
    }

    @Test
    @DisplayName("Quiz with No Questions: Exception Scenario")
    void testQuizWithNoQuestionsException()
    {
        quizQuestions= new ArrayList<>();
        quiz= new Quiz(1,"emptyQuiz",quizQuestions);

        Mockito.when(quizRepository.findById(1)).thenReturn(Optional.ofNullable(quiz));
        Assertions.assertThrows(ResourceNotFoundException.class,()->quizService.getQuizQuestions(1));
    }
}
