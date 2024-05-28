package org.triumers.kmsback.anonymousboard.query.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class QryAnonymousBoardDTO {

    private int id;
    private String nickname;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private String macAddress;
}
