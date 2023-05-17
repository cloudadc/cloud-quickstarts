package io.cloudadc.quiz.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

import io.cloudadc.quiz.services.gcp.datastore.QuestionService;
import io.cloudadc.quiz.services.gcp.domain.Answer;
import io.cloudadc.quiz.services.gcp.domain.Feedback;
import io.cloudadc.quiz.services.gcp.domain.Question;
import io.cloudadc.quiz.services.gcp.domain.QuizResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(value = "/api/quizzes")
public class QuizEndpoint {

    @Autowired
    private QuestionService questionService;


    @GetMapping(value = "{quiz}")
    public ResponseEntity<ObjectNode> getAllQuestions(@PathVariable String quiz) {
    	
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.set("questions", mapper.convertValue(questionService.getAllQuestions(quiz), JsonNode.class));
        
        return new ResponseEntity<ObjectNode>(root,HttpStatus.OK);
    }

    @PostMapping(value = "{quiz}")
    public ResponseEntity<QuizResult> processAnswers(@PathVariable String quiz, @RequestBody List<Answer> answers) {
        
    	List<Question> questions = questionService.getAllQuestions(quiz);
        long correctAnswers = answers.stream().filter(answer -> checkCorrectAnswer(answer, questions)).count();
        QuizResult result = new QuizResult();
        result.setCorrect(correctAnswers);
        result.setTotal(questions.size());
        
        return new ResponseEntity<QuizResult>(result,HttpStatus.OK);
    }

    @PostMapping(value = "/feedback/{quiz}")
    public ResponseEntity<ObjectNode> processFeedback(@PathVariable String quiz, @RequestBody Feedback feedback)throws Exception{
        
    	ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put("data","Feedback received");
        
        return new ResponseEntity<ObjectNode>(root,HttpStatus.OK);
    }

    private boolean checkCorrectAnswer(Answer answer,  List<Question> questions){
       
    	for(Question question : questions){
            if (answer.getId() == question.getId() && answer.getAnswer() == question.getCorrectAnswer()){
                return true;
            }
        }
        
    	return false;
    }
}
