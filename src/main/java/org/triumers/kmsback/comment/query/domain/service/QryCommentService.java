package org.triumers.kmsback.comment.query.domain.service;

import org.triumers.kmsback.comment.query.aggregate.entity.QryComment;

import java.util.List;

public interface QryCommentService {
    List<QryComment> getCommentsByPostId(Long postId);
    List<QryComment> getCommentsByUserId(Long userId);
}