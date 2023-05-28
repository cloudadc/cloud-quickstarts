package io.cloudadc.quiz.model;

public class Feedback {
	
    private String email;
    
    private String quiz;
    
    private String feedback;
    
    private long rating;
    
    private long timestamp;
    
    private float sentimentScore;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(float sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "email='" + email + '\'' +
                ", quiz='" + quiz + '\'' +
                ", feedback='" + feedback + '\'' +
                ", rating=" + rating +
                ", timestamp=" + timestamp +
                ", sentimentScore=" + sentimentScore +
                '}';
    }
}
