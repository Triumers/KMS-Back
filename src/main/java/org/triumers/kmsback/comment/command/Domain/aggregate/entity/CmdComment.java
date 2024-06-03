package org.triumers.kmsback.comment.command.Domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPost;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "tbl_comment")
public class CmdComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

}
