package io.cloudadc.quiz.model;

public class Answer {
	
	private String answerId;
	
    private String quiz;
    
    private String email;
    
    private long id;
    
    private long answer;
    
    private long correctAnswer;
    
    private long timestamp;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAnswer() {
        return answer;
    }

    public void setAnswer(long answer) {
        this.answer = answer;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public String getQuiz() {
		return quiz;
	}

	public void setQuiz(String quiz) {
		this.quiz = quiz;
	}

	public long getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(long correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@Override
    public String toString() {
        return "Answer{" +
                "answerId='" + answerId + '\'' +
                ", quiz='" + quiz + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", answer=" + answer +
                ", correctAnswer=" + correctAnswer +
                ", timestamp=" + timestamp +
                '}';
    }
}
