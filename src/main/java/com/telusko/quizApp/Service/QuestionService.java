package com.telusko.quizApp.Service;

import com.telusko.quizApp.Exception.ResourceNotFoundException;
import com.telusko.quizApp.entity.question;
import com.telusko.quizApp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService
{
    @Autowired
    QuestionDao questionDao;

    public List<question> getAllQuestions()
    {
        if(questionDao.findAll().isEmpty())
        {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "No Questions Found to be displayed");
        }
        return questionDao.findAll();
    }


    public List<question> getQuestionsByCategory(String category)
    {
        if(questionDao.findByCategory(category).isEmpty())
        {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "No Questions are available for the given category: "+ category);
        }
        return questionDao.findByCategory(category);
    }


    public String addQuestion(question question)
    {
        questionDao.save(question);
        return "success";
    }
}
