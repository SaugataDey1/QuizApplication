package com.telusko.quizApp.Service;

import com.telusko.quizApp.entity.question;
import com.telusko.quizApp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService
{
    @Autowired
    QuestionDao questionDao;

    public List<question> getAllQuestions()
    {
        return questionDao.findAll();
    }

    public List<question> getQuestionsByCategory(String category)
    {
        return questionDao.findByCategory(category);
    }


    public String addQuestion(question question)
    {
        questionDao.save(question);
        return "success";
    }
}
