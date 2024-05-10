package org.triumers.kmsback.anonymousboard.command.Application.dto;

import lombok.*;

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
    private int anonymousBoardId;
}
