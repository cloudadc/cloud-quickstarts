package io.cloudadc.quiz.web;

import io.cloudadc.quiz.model.Question;
import io.cloudadc.quiz.services.gcp.cloudstorage.ImageService;
import io.cloudadc.quiz.services.gcp.datastore.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class QuestionsController {

    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private ImageService imageService;

    @GetMapping("/questions/add")
    public String getForm(Model model) {
    	
        model.addAttribute("quizquestion", new Question());
        return "new_question";
    }

    @PostMapping("/questions/add")
    public String submitQuestion(Question question) throws IOException {
    	
    	String imageUrl = imageService.saveImage(question.getImage());
    	question.setImageUrl(imageUrl);
    	
    	System.out.println("Image URL is "+imageUrl);
    	
        questionService.createQuestion(question);
        return "redirect:/";
    }
}