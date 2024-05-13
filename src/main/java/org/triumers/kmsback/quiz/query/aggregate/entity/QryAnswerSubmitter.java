package org.triumers.kmsback.quiz.query.aggregate.entity;

public class QryAnswerSubmitter {
    private int id;
    private String answer;
    private int quizId;
    private int employeeId;

    public QryAnswerSubmitter() {
    }

    public QryAnswerSubmitter(int id, String answer, int quizId, int employeeId) {
        this.id = id;
        this.answer = answer;
        this.quizId = quizId;
        this.employeeId = employeeId;
    }

    public int getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    @Override
    public String toString() {
        return "QryAnswerSubmmitter{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", quizId=" + quizId +
                ", employeeId=" + employeeId +
                '}';
    }
}
