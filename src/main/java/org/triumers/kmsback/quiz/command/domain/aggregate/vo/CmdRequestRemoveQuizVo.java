package org.triumers.kmsback.quiz.command.domain.aggregate.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmdRequestRemoveQuizVo {
    private int id;
    private LocalDateTime deletedAt;
}
