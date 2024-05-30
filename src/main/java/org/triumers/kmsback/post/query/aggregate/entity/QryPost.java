package org.triumers.kmsback.post.query.aggregate.entity;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class QryPost {
    private Integer id;
    private Boolean isEditing;
    private String title;
    private String content;
    private String postImg;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private Integer authorId;
    private Integer originId;
    private Integer recentId;
    private Integer tabRelationId;
    private Integer categoryId;
}
