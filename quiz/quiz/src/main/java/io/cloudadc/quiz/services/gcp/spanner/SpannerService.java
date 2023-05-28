package io.cloudadc.quiz.services.gcp.spanner;

import org.springframework.stereotype.Service;

import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.DatabaseId;
import com.google.cloud.spanner.Spanner;
import com.google.cloud.spanner.SpannerOptions;
import com.google.cloud.spanner.Statement;
import com.google.cloud.spanner.ResultSet;

import io.cloudadc.quiz.model.LeaderBoardEntry;

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
	
	
	public List<LeaderBoardEntry> getQuizLeaders(){
        SpannerOptions options = SpannerOptions.newBuilder().build();
        Spanner spanner = options.getService();
        List<LeaderBoardEntry> leaderBoardEntries = new ArrayList<>();
        try {
            DatabaseId db = DatabaseId.of(options.getProjectId(), SPANNER_INSTANCE, SPANNER_DATABASE);
            DatabaseClient dbClient = spanner.getDatabaseClient(db);


            String query = "SELECT quiz, email, COUNT(*) AS score FROM Answers WHERE correct = answer GROUP BY quiz, email ORDER BY quiz, score DESC";

            ResultSet resultSet = dbClient.singleUse().executeQuery(Statement.of(query));
            while (resultSet.next()) {
                LeaderBoardEntry leaderBoardEntry = new LeaderBoardEntry();
                leaderBoardEntry.setQuiz(resultSet.getString(0));
                leaderBoardEntry.setEmail(resultSet.getString(1));
                leaderBoardEntry.setScore(resultSet.getLong(2));
                leaderBoardEntries.add(leaderBoardEntry);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return leaderBoardEntries;
    }

}
