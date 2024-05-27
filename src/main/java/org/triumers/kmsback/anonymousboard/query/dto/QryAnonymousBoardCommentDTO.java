package org.triumers.kmsback.anonymousboard.query.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QryAnonymousBoardCommentDTO {

    private int id;
    private String nickname;
    private String content;
    private LocalDateTime createdDate;
    private String macAddress;
    private int anonymousBoardId;
}
