package org.triumers.kmsback.post.query.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QryPostDTO {
    private Integer id;
    private String title;
    private String content;
    private String postImg;
    private LocalDate createdAt;
    private LocalDate deletedAt;
    private Integer authorId;
    private Integer originId;
    private Integer recentId;
    private Integer tabRelationId;
    private Integer categoryId;

    public QryPostDTO() {
    }

    public QryPostDTO(String title, String content, String postImg, LocalDate createdAt, Integer authorId,
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
