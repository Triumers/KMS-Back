package org.triumers.kmsback.post.command.Application.dto;

import lombok.Data;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdTag;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CmdPostAndTagsDTO {
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
    private List<String> tags;

    public CmdPostAndTagsDTO() {
    }

    public CmdPostAndTagsDTO(String title, String content, String postImg, LocalDateTime createdAt, Integer authorId,
                             Integer tabRelationId, List<String> tags) {
        this.title = title;
        this.content = content;
        this.postImg = postImg;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.tabRelationId = tabRelationId;
        this.tags = tags;
    }

    public CmdPostAndTagsDTO(String title, String content, String postImg, LocalDateTime createdAt,
                             Integer authorId, Integer originId, Integer tabRelationId, Integer categoryId,
                             List<String> tags) {
        this.title = title;
        this.content = content;
        this.postImg = postImg;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.originId = originId;
        this.tabRelationId = tabRelationId;
        this.categoryId = categoryId;
        this.tags = tags;
    }

    public CmdPostAndTagsDTO(Integer id, String title, String content, String postImg, LocalDateTime createdAt,
                             LocalDateTime deletedAt, Integer authorId, Integer originId, Integer recentId,
                             Integer tabRelationId, Integer categoryId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postImg = postImg;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.authorId = authorId;
        this.originId = originId;
        this.recentId = recentId;
        this.tabRelationId = tabRelationId;
        this.categoryId = categoryId;
    }
}
