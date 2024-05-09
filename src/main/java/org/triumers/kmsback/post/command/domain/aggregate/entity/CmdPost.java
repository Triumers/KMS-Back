package org.triumers.kmsback.post.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "tbl_post")
@Data
public class CmdPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IS_EDITING", nullable = false)
    private Boolean isEditing = false;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDate createdAt;

    @Column(name = "DELETED_AT")
    private LocalDate deletedAt;

    @Column(name = "AUTHOR_ID", nullable = false)
    private Integer authorId;

    @Column(name = "ORIGIN_ID")
    private Integer originId;

    @Column(name = "RECENT_ID")
    private Integer recentId;

    @Column(name = "TAB_RELATION_ID")
    private Integer tabRelationId;

    public CmdPost() {

    }
    public CmdPost(String title, String content, LocalDate createdAt, Integer authorId, Integer tabRelationId) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.tabRelationId = tabRelationId;
    }

    public CmdPost(String title, String content, LocalDate createdAt, Integer authorId, Integer originId, Integer tabRelationId) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.originId = originId;
        this.tabRelationId = tabRelationId;
    }
}
