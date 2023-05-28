package io.cloudadc.quiz.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

import io.cloudadc.quiz.model.Answer;
import io.cloudadc.quiz.model.Feedback;
import io.cloudadc.quiz.model.Question;
import io.cloudadc.quiz.model.QuizResult;
import io.cloudadc.quiz.services.gcp.datastore.QuestionService;
import io.cloudadc.quiz.services.gcp.pubsub.PublishService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/quizzes")
public class QuizEndpoint {
	
	Logger logger = LoggerFactory.getLogger(QuizEndpoint.class);

    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private PublishService publishService;


    @GetMapping(value = "{quiz}")
    public ResponseEntity<ObjectNode> getAllQuestions(@PathVariable String quiz) {
    	
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.set("questions", mapper.convertValue(questionService.getAllQuestions(quiz), JsonNode.class));
        
        return new ResponseEntity<ObjectNode>(root,HttpStatus.OK);
    }

    @PostMapping(value = "{quiz}")
    public ResponseEntity<QuizResult> processAnswers(@PathVariable String quiz, @RequestBody List<Answer> answers) throws Exception {
        
    	List<Question> questions = questionService.getAllQuestions(quiz);
        long correctAnswers = answers.stream().filter(answer -> checkCorrectAnswer(answer, questions)).count();
        QuizResult result = new QuizResult();
        result.setCorrect(correctAnswers);
        result.setTotal(questions.size());
        
        logger.info("push " + result + " to answers topic");
        
        publishService.publishAnswers(answers, quiz);
        
        return new ResponseEntity<QuizResult>(result,HttpStatus.OK);
    }

    @PostMapping(value = "/feedback/{quiz}")
    public ResponseEntity<ObjectNode> processFeedback(@PathVariable String quiz, @RequestBody Feedback feedback)throws Exception{
        
    	ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        publishService.publishFeedback(feedback);
        
        root.put("data","Feedback received");
        
        return new ResponseEntity<ObjectNode>(root,HttpStatus.OK);
    }

    private boolean checkCorrectAnswer(Answer answer,  List<Question> questions){
       
    	for(Question question : questions){
    		
    		if(answer.getId() == question.getId()){
                answer.setCorrectAnswer(question.getCorrectAnswer());
            }
    		
            if (answer.getId() == question.getId() && answer.getAnswer() == question.getCorrectAnswer()){
                return true;
            }
        }
        
    	return false;
    }
}
