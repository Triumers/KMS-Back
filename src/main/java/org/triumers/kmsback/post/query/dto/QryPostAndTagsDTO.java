package org.triumers.kmsback.post.query.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class QryPostAndTagsDTO {
    private Integer id;
    private String title;
    private String content;
    private LocalDate createdAt;
    private Integer authorId;
    private Integer originId;
    private Integer recentId;
    private Integer tabRelationId;
    private List<QryTagDTO> tags;
    private List<QryPostDTO> history;

    public QryPostAndTagsDTO(Integer id, String title, String content, LocalDate createdAt, Integer authorId,
                             Integer originId, Integer recentId, Integer tabRelationId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.originId = originId;
        this.recentId = recentId;
        this.tabRelationId = tabRelationId;
    }

    public QryPostAndTagsDTO(String title, String content, LocalDate createdAt, Integer authorId, Integer tabRelationId, List<QryTagDTO> tags) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.tabRelationId = tabRelationId;
        this.tags = tags;
    }

    public QryPostAndTagsDTO(String title, String content, LocalDate createdAt, Integer authorId,
                             Integer originId, Integer tabRelationId, List<QryTagDTO> tags) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.originId = originId;
        this.tabRelationId = tabRelationId;
        this.tags = tags;
    }
}
