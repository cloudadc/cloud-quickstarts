package io.cloudadc.quiz.services.gcp.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import io.cloudadc.quiz.model.Answer;
import io.cloudadc.quiz.model.Feedback;

@Service
public class PublishService {
	
	private static final String PROJECT_ID = ServiceOptions.getDefaultProjectId();
	
    private static final String FEEDBACK_TOPIC_NAME = "feedback";
    
    public static final String ANSWER_TOPIC_NAME = "answers";
    
    Logger logger = LoggerFactory.getLogger(PublishService.class);
    
    
    public void publishFeedback(Feedback feedback) throws Exception {
    	
        ObjectMapper mapper = new ObjectMapper();
        String feedbackMessage = mapper.writeValueAsString(feedback);
        publishMessage(feedbackMessage, FEEDBACK_TOPIC_NAME);
    }
    
    public void publishAnswers(List<Answer> answers, String quiz) throws Exception {
    	
        for(Answer answer : answers){
            answer.setQuiz(quiz);
            ObjectMapper mapper = new ObjectMapper();
            String feedbackMessage = mapper.writeValueAsString(answer);
            publishMessage(feedbackMessage, ANSWER_TOPIC_NAME);
        }
    }


	private void publishMessage(String message, String topic) throws Exception {

		TopicName topicName = TopicName.of(PROJECT_ID, topic);
		
        Publisher publisher = null;
        
        try {
        	
			publisher = Publisher.newBuilder(topicName).build();
			
			ByteString data = ByteString.copyFromUtf8(message);
			PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
			
			ApiFuture<String> future = publisher.publish(pubsubMessage);
			
			ApiFutures.addCallback(future, new ApiFutureCallback<String>() {

				@Override
				public void onFailure(Throwable t) {
					logger.error("Error publishing message : " + message + ", " + t.getMessage());
				}

				@Override
				public void onSuccess(String messageId) {
					logger.info("Published message ID: " + messageId);
				}}, MoreExecutors.directExecutor());
			
		} finally {
			
			if (publisher != null) {
				publisher.shutdown();
			}
			
		}
		
	}

}
