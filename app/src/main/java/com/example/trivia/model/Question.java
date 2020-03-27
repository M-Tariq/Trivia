package com.example.trivia.model;

public class Question {
    private String answer; //question
    private Boolean answerTrue; //answer

    public Question() {
    }

    public Question(String answer, Boolean answerTrue) {
        this.answer = answer;
        this.answerTrue = answerTrue;
    }

    @Override
    public String toString() {
        return "Question{" +
                "answer='" + answer + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(Boolean answerTrue) {
        this.answerTrue = answerTrue;
    }
}
