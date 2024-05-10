package org.triumers.kmsback.post.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_post_tag")
@Data
public class CmdPostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TAG_ID", nullable = false)
    private Integer tagId;

    @Column(name = "POST_ID", nullable = false)
    private Integer postId;

    public CmdPostTag() {
    }

    public CmdPostTag(Integer tagId, Integer postId) {
        this.tagId = tagId;
        this.postId = postId;
    }
}
