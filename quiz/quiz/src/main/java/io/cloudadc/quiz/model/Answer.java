package io.cloudadc.quiz.model;

public class Answer {
	
    private String email;
    
    private long id;
    
    private long answer;
    
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

    @Override
    public String toString() {
        return "Answer{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", answer=" + answer +
                ", timestamp=" + timestamp +
                '}';
    }
}
