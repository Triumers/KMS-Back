package org.triumers.kmsback.quiz.query.dto;

public class QryAnswerSubmitterDTO {
    private int id;
    private String answer;
    private int quizId;
    private int employeeId;

    public QryAnswerSubmitterDTO() {
    }

    public QryAnswerSubmitterDTO(int id, String answer, int quizId, int employeeId) {
        this.id = id;
        this.answer = answer;
        this.quizId = quizId;
        this.employeeId = employeeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "QryAnswerSubmitterDTO{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", quizId=" + quizId +
                ", employeeId=" + employeeId +
                '}';
    }
}
