package org.triumers.kmsback.comment.query.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.comment.query.aggregate.entity.QryComment;
import org.triumers.kmsback.comment.query.repository.CommentMapper;

import java.util.List;

@Service
public class QryCommentServiceImpl implements QryCommentService{
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<QryComment> getCommentsByUserId(Long userId) {
        return commentMapper.selectCommentsByUserId(userId);
    }

    @Override
    public List<QryComment> getCommentsByPostId(Long postId) {
        return commentMapper.selectCommentsByPostId(postId);
    }
}
