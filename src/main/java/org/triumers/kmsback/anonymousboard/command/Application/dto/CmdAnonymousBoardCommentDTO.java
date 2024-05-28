package org.triumers.kmsback.anonymousboard.command.Application.dto;

import lombok.*;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoard;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CmdAnonymousBoardCommentDTO {

    private int id;
    private String nickname;
    private String content;
    private LocalDateTime createdDate;
    private String macAddress;
    private CmdAnonymousBoard anonymousBoard;
}
