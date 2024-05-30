package org.triumers.kmsback.comment.command.Domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.triumers.kmsback.common.translation.entity.Post;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "tbl_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 댓글 ID

    @Column(nullable = false)
    private String content;  // 댓글 내용

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;  // 생성 시간

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;  // 삭제 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Employee author;  // 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  // 게시글


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Employee getAuthor() {
        return author;
    }

    public void setAuthor(Employee author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(QryPostAndTagsDTO post) {
        this.post = post;
    }
}
