package org.triumers.kmsback.anonymousboard.command.Application.dto;

import lombok.*;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoard;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CmdAnonymousBoardDTO {

    private int id;
    private String nickname;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private String macAddress;

    public CmdAnonymousBoard toEntity() {
        return new CmdAnonymousBoard(
                this.id,
                this.nickname,
                this.title,
                this.content,
                this.createdDate,
                this.macAddress
        );
    }

}
