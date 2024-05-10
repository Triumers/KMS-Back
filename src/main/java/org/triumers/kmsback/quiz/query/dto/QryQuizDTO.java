package org.triumers.kmsback.quiz.query.dto;

public class QryQuizDTO {
    private int id;
    private String content;
    private String answer;
    private String commentary;
    private boolean status;
    private int questionId;
    private int postId;
    private int topTapId;

    public QryQuizDTO() {
    }

    public QryQuizDTO(boolean status) {
        this.status = status;
    }

    public QryQuizDTO(int id, String content, String answer, String commentary, boolean status, int questionId, int postId, int topTapId) {
        this.id = id;
        this.content = content;
        this.answer = answer;
        this.commentary = commentary;
        this.status = status;
        this.questionId = questionId;
        this.postId = postId;
        this.topTapId = topTapId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getTopTapId() {
        return topTapId;
    }

    public void setTopTapId(int topTapId) {
        this.topTapId = topTapId;
    }

    @Override
    public String toString() {
        return "QryQuizDTO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", answer='" + answer + '\'' +
                ", commentary='" + commentary + '\'' +
                ", status=" + status +
                ", questionId=" + questionId +
                ", postId=" + postId +
                ", topTapId=" + topTapId +
                '}';
    }
}
