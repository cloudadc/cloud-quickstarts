package io.cloudadc.quiz.feedback.handler;

import org.springframework.stereotype.Service;

import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.DatabaseId;
import com.google.cloud.spanner.Spanner;
import com.google.cloud.spanner.SpannerException;
import com.google.cloud.spanner.SpannerOptions;
import com.google.cloud.spanner.Mutation;

import io.cloudadc.quiz.model.Feedback;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpannerService {
	
	private static final String SPANNER_INSTANCE = "quiz-instance";
	
	private static final String SPANNER_DATABASE = "quiz-database";
	
	private static final SpannerService spannerService= new SpannerService(){};
	
	public static SpannerService create(){
        return spannerService;
    }
	
	public void insertFeedback(Feedback feedback) {
		
		SpannerOptions options = SpannerOptions.newBuilder().build();
        Spanner spanner = options.getService();
        
        try {
			DatabaseId db = DatabaseId.of(options.getProjectId(), SPANNER_INSTANCE, SPANNER_DATABASE);
			DatabaseClient dbClient = spanner.getDatabaseClient(db);
			
			List<Mutation> mutations = new ArrayList<>();
			
			mutations.add( 
					Mutation.newInsertBuilder("Feedback")
			        .set("feedbackId")
			        .to(feedback.getEmail()+'_'+feedback.getQuiz()+"_"+feedback.getTimestamp())
			        .set("email")
			        .to(feedback.getEmail())
			        .set("quiz")
			        .to(feedback.getQuiz())
			        .set("feedback")
			        .to(feedback.getFeedback())
			        .set("rating")
			        .to(feedback.getRating())
			        .set("score")
			        .to(feedback.getSentimentScore())
			        .set("timestamp")
			        .to(feedback.getTimestamp())
			        .build());
			
			dbClient.write(mutations);
		} catch (SpannerException e) {
			throw e;
		}
	}
	

}
