package io.cloudadc.quiz.feedback.handler;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;

public class LanguageService {
	
	private static final LanguageService languageService = new LanguageService(){};
	
	public static LanguageService create() {
        return languageService;
    }
	
	private LanguageService() {
		
	}
	

	public float analyseSentiment(String feedback) throws Exception {
		
		try (LanguageServiceClient language = LanguageServiceClient.create()) {
			
			Document doc = Document.newBuilder().setContent(feedback).setType(Document.Type.PLAIN_TEXT).build();
			Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
			
			System.out.printf("Feedback Text: %s%n", feedback);
            System.out.printf("Sentiment: %s, %s%n", sentiment.getScore(), sentiment.getMagnitude());
            return sentiment.getScore();
		}
		
	}
}
