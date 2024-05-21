package org.triumers.kmsback.quiz.command.Application.dto;

import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime deletedAt;

    public CmdAnswerSubmitterDTO(int id, String answer, String commentary, boolean status, int quizId, int employeeId) {
        this.id = id;
        this.answer = answer;
        Commentary = commentary;
        this.status = status;
        this.quizId = quizId;
        this.employeeId = employeeId;
    }
}
