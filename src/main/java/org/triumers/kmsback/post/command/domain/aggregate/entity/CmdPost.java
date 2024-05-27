package org.triumers.kmsback.post.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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

    @Column(name = "POST_IMG")
    private String postImg;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    @Column(name = "AUTHOR_ID", nullable = false)
    private Integer authorId;

    @Column(name = "ORIGIN_ID")
    private Integer originId;

    @Column(name = "RECENT_ID")
    private Integer recentId;

    @Column(name = "TAB_RELATION_ID")
    private Integer tabRelationId;

    @Column(name = "CATEGORY_ID")
    private Integer categoryId;

    public CmdPost() {

    }

    public CmdPost(Integer id, String title, String content, String postImg, LocalDateTime createdAt,
                   Integer authorId, Integer originId, Integer tabRelationId, Integer categoryId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postImg = postImg;
        this.createdAt = createdAt;
        this.authorId = authorId;
        this.originId = originId;
        this.tabRelationId = tabRelationId;
        this.categoryId = categoryId;
    }
}
