package org.triumers.kmsback.post.command.Application.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CmdPostDTO {
    private Integer id;
    private String title;
    private String content;
    private LocalDate createdAt;
    private LocalDate deletedAt;
    private Integer authorId;
    private Integer originId;
    private Integer recentId;
    private Integer tabRelationId;

    public CmdPostDTO() {
    }

    public CmdPostDTO(String title, String content, LocalDate createdAt, Integer authorId, Integer tabRelationId) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.tabRelationId = tabRelationId;
    }

    public CmdPostDTO(String title, String content, LocalDate createdAt, Integer authorId,
                      Integer originId, Integer tabRelationId) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.originId = originId;
        this.tabRelationId = tabRelationId;
    }

}
