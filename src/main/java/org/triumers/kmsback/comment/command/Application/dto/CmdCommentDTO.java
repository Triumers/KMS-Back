package org.triumers.kmsback.comment.command.Application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class CmdCommentDTO {
    private Integer id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private Long authorId;
    private Long postId;
}
