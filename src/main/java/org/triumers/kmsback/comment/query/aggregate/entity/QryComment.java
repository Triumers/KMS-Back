package org.triumers.kmsback.comment.query.aggregate.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class QryComment {
    private Long id;
    private Long postId;
    private Long authorId;
    private String content;
    private String author;
    private LocalDateTime createdAt;

}
