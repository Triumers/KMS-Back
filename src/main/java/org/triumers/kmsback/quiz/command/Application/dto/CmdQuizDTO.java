package org.triumers.kmsback.quiz.command.Application.dto;

import lombok.*;

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
    private int topTapId;
}
