package org.triumers.kmsback.post.query.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QryPostDTO {
    private Integer id;
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

    public QryPostDTO() {
    }

    public QryPostDTO(String title, String content, String postImg, LocalDateTime createdAt, Integer authorId,
                      Integer originId, Integer tabRelationId) {
        this.title = title;
        this.content = content;
        this.postImg = postImg;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.originId = originId;
        this.tabRelationId = tabRelationId;
    }
}
