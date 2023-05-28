package io.cloudadc.quiz.answers.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.TopicName;

import io.cloudadc.quiz.model.Answer;


@SpringBootApplication
public class Main implements CommandLineRunner {
	
	private static final String TOPIC_NAME = "answers";
	
	Logger logger = LoggerFactory.getLogger(Main.class);
	
	@Override
	public void run(String... args) throws Exception {

		logger.info("Quiz Answers Handler");
		
		String projectId = System.getenv("GCLOUD_PROJECT");
		logger.info("Project: " + projectId);
		
        TopicName topic = TopicName.of(projectId, TOPIC_NAME);
        
        SpannerService spannerService = SpannerService.create();
		
        ProjectSubscriptionName subscription = ProjectSubscriptionName.of(projectId, "answer-subscription");
        logger.info("Starting answer subscriber...");
        
        try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
        	
        	logger.info("Creating subscription to answers...");
            subscriptionAdminClient.createSubscription(subscription, topic, PushConfig.getDefaultInstance(), 0);
            logger.info("Created.");
        }
        
        MessageReceiver receiver =
                new MessageReceiver() {
        	
                    @Override
                    public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
                        String fb = message.getData().toStringUtf8();
                        consumer.ack();

                        logger.info("\n\n**************\n\nId : " + message.getMessageId());
                        logger.info("\n\n**************\n\nData : " + fb);
                        consumer.ack();
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            Answer answer = mapper.readValue(fb, Answer.class);
                            spannerService.insertAnswer(answer);
                        } catch (Exception e) {
                            logger.error("PubSub receiver failed: "+ e.getMessage());
                            e.printStackTrace();
                        }


                    }
                };
		
                Subscriber subscriber = null;
                try {
                    subscriber = Subscriber.newBuilder(subscription, receiver).build();
                    subscriber.addListener(
                            new Subscriber.Listener() {
                                @Override
                                public void failed(Subscriber.State from, Throwable failure) {
                                    // Handle failure. This is called when the Subscriber encountered a fatal error and is shutting down.
                                    System.err.println(failure);
                                }
                            },
                            MoreExecutors.directExecutor());
                    subscriber.startAsync().awaitRunning();
                    logger.info("Started. Press any key to quit and remove subscription");

                    System.in.read();

                } finally {
                    if (subscriber != null) {
                        subscriber.stopAsync().awaitTerminated();
                    }
                    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {

                    	logger.info("Deleting subscription...");
                        subscriptionAdminClient.deleteSubscription(subscription);
                        logger.info("Deleted.");
                    }
                }
		
		
    }

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	
}
