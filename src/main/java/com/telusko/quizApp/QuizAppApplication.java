package com.telusko.quizApp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@OpenAPIDefinition(info = @Info(title = "Quiz Application" , description = "Application for creating Quiz on various categories of questions for user", version = "1.0"))
public class QuizAppApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(QuizAppApplication.class, args);
	}
}
