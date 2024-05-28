package org.triumers.kmsback.quiz.query.aggregate.entity;

import java.time.LocalDate;

public class QryAnswerSubmitter {
    private int id;
    private String answer;
    private String commentary;
    private boolean status;
    private int quizId;
    private int employeeId;
    private LocalDate createdAt;

    public QryAnswerSubmitter() {
    }

    public QryAnswerSubmitter(int id, String answer, String commentary, boolean status, int quizId, int employeeId, LocalDate createdAt) {
        this.id = id;
        this.answer = answer;
        this.commentary = commentary;
        this.status = status;
        this.quizId = quizId;
        this.employeeId = employeeId;
        this.createdAt = createdAt;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCommentary() {
        return this.commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getQuizId() {
        return this.quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String toString() {
        return "QryAnswerSubmitter{id=" + this.id + ", answer='" + this.answer + "', commentary='" + this.commentary + "', status=" + this.status + ", quizId=" + this.quizId + ", employeeId=" + this.employeeId + ", createdAt=" + this.createdAt + "}";
    }
}
