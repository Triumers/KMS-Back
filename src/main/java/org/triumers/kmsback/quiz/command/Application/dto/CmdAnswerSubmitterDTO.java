package org.triumers.kmsback.quiz.command.Application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmdAnswerSubmitterDTO {
    private int id;
    private String answer;
    private String Commentary;
    private boolean status;
    private int quizId;
    private int employeeId;
}
