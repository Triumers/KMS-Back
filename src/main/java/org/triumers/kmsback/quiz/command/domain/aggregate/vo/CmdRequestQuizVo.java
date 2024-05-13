package org.triumers.kmsback.quiz.command.domain.aggregate.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmdRequestQuizVo {
    private int id;
    private String content;
    private String answer;
    private String commentary;
    private boolean status;
    private int questionerId;
    private int postId;
    private int topTapId;
}
