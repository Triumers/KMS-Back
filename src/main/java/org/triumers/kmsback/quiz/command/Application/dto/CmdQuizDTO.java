package org.triumers.kmsback.quiz.command.Application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmdQuizDTO {
    private int id;
    private String content;
    private String answer;
    private String commentary;
    private boolean status;
    private int questionerId;
    private int postId;
    private int tapId;
    private LocalDateTime deletedAt;

    public CmdQuizDTO(int id, String content, String answer, String commentary, boolean status, int questionerId, int postId, int tapId) {
        this.id = id;
        this.content = content;
        this.answer = answer;
        this.commentary = commentary;
        this.status = status;
        this.questionerId = questionerId;
        this.postId = postId;
        this.tapId = tapId;
    }
}
