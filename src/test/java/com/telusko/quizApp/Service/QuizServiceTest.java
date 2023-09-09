package com.telusko.quizApp.Service;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

        Mockito.verify(quizRepository).save(quiz);

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
}
