package io.cloudadc.quiz.model;

public class LeaderBoardEntry {
	
	private String quiz;
	
    private String email;
    
    private long score;
    
    public String getQuiz() {
		return quiz;
	}

	public void setQuiz(String quiz) {
		this.quiz = quiz;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	@Override
    public String toString() {
        return "LeaderBoardEntry{" +
                "quiz='" + quiz + '\'' +
                ", email='" + email + '\'' +
                ", score=" + score +
                '}';
    }

}
