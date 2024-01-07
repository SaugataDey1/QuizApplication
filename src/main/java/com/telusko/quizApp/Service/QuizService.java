package com.telusko.quizApp.Service;

import com.telusko.quizApp.Exception.ResourceNotFoundException;
import com.telusko.quizApp.dao.QuestionDao;
import com.telusko.quizApp.dao.QuizDao;
import com.telusko.quizApp.entity.QuestionWrapper;
import com.telusko.quizApp.entity.Quiz;
import com.telusko.quizApp.entity.Response;
import com.telusko.quizApp.entity.question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class QuizService
{
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title)
    {
        List<question> questions = questionDao.findRandomQuestionByCategory(category, numQ);

        if(questions.isEmpty())
        {
            log.error("No questions found in given category");
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"No Questions are found in given category for creating quiz");
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Created Quiz!", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id)
    {
        Optional<Quiz> optionalQuiz = quizDao.findById(id);
        Quiz quiz;

        if(optionalQuiz.isPresent())
            quiz = optionalQuiz.get();
        else {
            log.error("Quiz not found....");
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Quiz with the given Id not found");
        }

        if(quiz.getQuestions().isEmpty()) {
            log.error("Quiz has no questions...");
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND,"Quiz is empty without any questions");
        }

        List<question> questionsFromDB = quiz.getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(question q : questionsFromDB)
        {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }


    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses)
    {
        Optional<Quiz> optionalQuiz = quizDao.findById(id);
        Quiz quiz;

        if(optionalQuiz.isPresent())
            quiz = optionalQuiz.get();
        else
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Response is invalid as Quiz with given ID not found");

        List<question> questions = quiz.getQuestions();
        int right = 0;
        int i=0;
        for(Response response : responses)
        {
            if(response.getResponse()==null||response.getResponse().isEmpty()) {
                log.error("Empty Response Body....");
                throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Response is empty");
            }

            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;

            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
