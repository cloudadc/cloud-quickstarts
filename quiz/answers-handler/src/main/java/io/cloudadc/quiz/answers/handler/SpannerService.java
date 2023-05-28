package io.cloudadc.quiz.answers.handler;

import org.springframework.stereotype.Service;

import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.DatabaseId;
import com.google.cloud.spanner.Spanner;
import com.google.cloud.spanner.SpannerException;
import com.google.cloud.spanner.SpannerOptions;
import com.google.cloud.spanner.Mutation;

import io.cloudadc.quiz.model.Answer;

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
	
	public void insertAnswer(Answer answer) {
        SpannerOptions options = SpannerOptions.newBuilder().build();
        Spanner spanner = options.getService();
        try {
            DatabaseId db = DatabaseId.of(options.getProjectId(), SPANNER_INSTANCE, SPANNER_DATABASE );
            DatabaseClient dbClient = spanner.getDatabaseClient(db);

            List<Mutation> mutations = new ArrayList<>();

            mutations.add(
                    Mutation.newInsertBuilder("Answers")
                            .set("answerId")
                            .to(answer.getEmail()+'_'+answer.getQuiz()+"_"+answer.getTimestamp())
                            .set("id")
                            .to(answer.getId())
                            .set("email")
                            .to(answer.getEmail())
                            .set("quiz")
                            .to(answer.getQuiz())
                            .set("answer")
                            .to(answer.getAnswer())
                            .set("correct")
                            .to(answer.getCorrectAnswer())
                            .set("timestamp")
                            .to(answer.getTimestamp())
                            .build());

            dbClient.write(mutations);
        }catch(SpannerException e){
        	throw e;
        }
    }
	

}
