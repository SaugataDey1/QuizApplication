package com.telusko.quizApp.Controller;

import com.telusko.quizApp.entity.question;
import com.telusko.quizApp.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController
{
    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
     public ResponseEntity<List<question>> getAllQuestions()
     {
         try
         {
             return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }

         return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
     }

     @GetMapping("category/{category}")
     public ResponseEntity<List<question>> getQuestionsByCategory(@PathVariable String category)
     {
         try
         {
             return new ResponseEntity<>(questionService.getQuestionsByCategory(category), HttpStatus.OK);
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }

         return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
     }

     @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody question question)
    {
        questionService.addQuestion(question);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }
}
