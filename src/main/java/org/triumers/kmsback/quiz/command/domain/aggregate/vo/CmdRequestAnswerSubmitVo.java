package org.triumers.kmsback.quiz.command.domain.aggregate.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmdRequestAnswerSubmitVo {
    private int id;
    private String answer;
    private String Commentary;
    private boolean status;
    private int quizId;
    private int employeeId;
}
