package org.triumers.kmsback.post.command.Application.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CmdPostAndTagsDTO {
    private Integer id;
    private String title;
    private String content;
    private LocalDate createdAt;
    private LocalDate deletedAt;
    private Integer authorId;
    private Integer originId;
    private Integer recentId;
    private Integer tabRelationId;
    private List<CmdTagDTO> tags;

    public CmdPostAndTagsDTO() {
    }

    public CmdPostAndTagsDTO(String title, String content, LocalDate createdAt, Integer authorId, Integer tabRelationId, List<CmdTagDTO> tags) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.tabRelationId = tabRelationId;
        this.tags = tags;
    }

    public CmdPostAndTagsDTO(String title, String content, LocalDate createdAt, Integer authorId,
                             Integer originId, Integer tabRelationId) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.originId = originId;
        this.tabRelationId = tabRelationId;
    }
}
