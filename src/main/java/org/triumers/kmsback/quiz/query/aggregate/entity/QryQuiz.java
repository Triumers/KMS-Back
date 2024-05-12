package org.triumers.kmsback.quiz.query.aggregate.entity;

public class QryQuiz {
    private int id;
    private String content;
    private String answer;
    private String commentary;
    private boolean status;
    private int questionerId;
    private int postId;
    private int topTapId;

    public QryQuiz(boolean status) {
    }

    public QryQuiz(int id, String content, String answer, String commentary, boolean status, int questionerId, int postId, int topTapId) {
        this.id = id;
        this.content = content;
        this.answer = answer;
        this.commentary = commentary;
        this.status = status;
        this.questionerId = questionerId;
        this.postId = postId;
        this.topTapId = topTapId;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAnswer() {
        return answer;
    }

    public String getCommentary() {
        return commentary;
    }

    public boolean isStatus() {
        return status;
    }

    public int getQuestionerId() {
        return questionerId;
    }

    public int getPostId() {
        return postId;
    }

    public int getTopTapId() {
        return topTapId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setQuestionerId(int questionerId) {
        this.questionerId = questionerId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setTopTapId(int topTapId) {
        this.topTapId = topTapId;
    }

    @Override
    public String toString() {
        return "QryQuiz{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", answer='" + answer + '\'' +
                ", commentary='" + commentary + '\'' +
                ", status=" + status +
                ", questionId=" + questionerId +
                ", postId=" + postId +
                ", topTapId=" + topTapId +
                '}';
    }
}

