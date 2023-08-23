package com.telusko.quizApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class QuizAppApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(QuizAppApplication.class, args);
	}
}
