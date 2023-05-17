package io.cloudadc.quiz.services.gcp.domain;

public class QuizResult {
    private long correct;
    private long total;

    public long getCorrect() {
        return correct;
    }

    public void setCorrect(long correct) {
        this.correct = correct;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "QuizResult{" +
                "correct=" + correct +
                ", total=" + total +
                '}';
    }
}
