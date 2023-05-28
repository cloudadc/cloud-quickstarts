package io.cloudadc.quiz.questions.loader;

import static java.lang.System.out;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.cloudadc.quiz.model.Question;

@SpringBootApplication
public class Main implements CommandLineRunner {
	
	private static String OBJ_NAME = "figure.png";
	
	private static String GCLOUD_PROJECT = System.getenv("GCLOUD_PROJECT");
	
	private static String GCLOUD_BUCKET = System.getenv("GCLOUD_BUCKET");
	
	
	static {
		
		if(GCLOUD_PROJECT == null || GCLOUD_BUCKET == null || GCLOUD_PROJECT.length() <= 0 || GCLOUD_BUCKET.length() <= 0) {
			throw new RuntimeException("Can not get GCLOUD_PROJECT and GCLOUD_BUCKET from evn");
		}
		
		System.setProperty("google.project.id", GCLOUD_PROJECT);
		System.setProperty("google.storage.bucket", GCLOUD_BUCKET);
		
	}
	
	Logger log = LoggerFactory.getLogger(Main.class);

	@Override
	public void run(String... args) throws Exception {

		log.info("Entity Factory Creating Initial Questions");
		
		ImageLoader imageService = ImageLoader.create();
		
		File image = Paths.get("etc/img/libai.png").toFile();
		if(!image.exists()) {
			if(args.length <= 0) {
				throw new RuntimeException("Pass the file path via args");
			}
			image = Paths.get(args[0]).toFile();
		}
		
		String filePath = image.getAbsolutePath();
		
		imageService.uploadObject(OBJ_NAME, filePath);
		
		
		
		QuestionsLoader questionService = QuestionsLoader.create();
		
		buildQuestions().forEach(question -> questionService.createQuestion(question));

		log.info("Questions Stored for Quiz GCP");
        questionService.getAllQuestions("gcp").forEach(out::println);
        
        log.info("Questions Stored for Quiz Places");
        questionService.getAllQuestions("places").forEach(out::println);
        
        log.info("Questions Stored for Quiz People");
        questionService.getAllQuestions("people").forEach(out::println);
		
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	static List<Question> buildQuestions(){
	       List<Question> questions = new ArrayList<>();

	       Question question = new Question.Builder()
	               .withQuiz("gcp")
	               .withAuthor("Nigel")
	               .withTitle("Which company runs GCP?")
	               .withAnswerOne("Amazon")
	               .withAnswerTwo("Google")
	               .withAnswerThree("IBM")
	               .withAnswerFour("Microsoft")
	               .withCorrectAnswer(2)
	               .withImageUrl("")
	               .build();
	       questions.add(question);

	       question = new Question.Builder()
	                .withQuiz("gcp")
	                .withAuthor("Nigel")
	                .withTitle("Which GCP product is NoSQL?")
	                .withAnswerOne("Compute Engine")
	                .withAnswerTwo("DataStore")
	                .withAnswerThree("Spanner")
	                .withAnswerFour("BigQuery")
	                .withCorrectAnswer(2)
	                .withImageUrl("")
	                .build();
	        questions.add(question);

	        question = new Question.Builder()
	                .withQuiz("gcp")
	                .withAuthor("Nigel")
	                .withTitle("Which GCP product is an Object Store?")
	                .withAnswerOne("Cloud Storage")
	                .withAnswerTwo("Datastore")
	                .withAnswerThree("Big Table")
	                .withAnswerFour("All of the above")
	                .withCorrectAnswer(1)
	                .withImageUrl("")
	                .build();
	        questions.add(question);

	        question = new Question.Builder()
	                .withQuiz("places")
	                .withAuthor("Nigel")
	                .withTitle("What is the capital of France?")
	                .withAnswerOne("Berlin")
	                .withAnswerTwo("London")
	                .withAnswerThree("Paris")
	                .withAnswerFour("Stockholm")
	                .withCorrectAnswer(3)
	                .withImageUrl("")
	                .build();
	        questions.add(question);
	        
	        question = new Question.Builder()
	                .withQuiz("people")
	                .withAuthor("Kylin")
	                .withTitle("Who is the person?")
	                .withAnswerOne("Libai")
	                .withAnswerTwo("Dufu")
	                .withAnswerThree("Baijuyi")
	                .withAnswerFour("wangwei")
	                .withCorrectAnswer(1)
	                .withImageUrl("https://storage-download.googleapis.com/" + System.getProperty("google.storage.bucket") + "/" + OBJ_NAME )
	                .build();
	        questions.add(question);
	       return questions;
	    }

}
