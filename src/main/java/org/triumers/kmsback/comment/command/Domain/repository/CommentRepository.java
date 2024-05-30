package org.triumers.kmsback.comment.command.Domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.triumers.kmsback.comment.command.Domain.aggregate.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
