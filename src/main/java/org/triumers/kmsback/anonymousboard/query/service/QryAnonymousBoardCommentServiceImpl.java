package org.triumers.kmsback.anonymousboard.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.query.repository.QryAnonymousBoardCommentMapper;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QryAnonymousBoardCommentServiceImpl implements QryAnonymousBoardCommentService {

    private final QryAnonymousBoardCommentMapper qryAnonymousBoardCommentMapper;

    @Autowired
    public QryAnonymousBoardCommentServiceImpl(QryAnonymousBoardCommentMapper qryAnonymousBoardCommentMapper) {
        this.qryAnonymousBoardCommentMapper = qryAnonymousBoardCommentMapper;
    }

    @Override
    public Page<QryAnonymousBoardCommentDTO> findAllAnonymousBoardComment(int anonymousBoardId, Pageable pageable) {
        List<QryAnonymousBoardCommentDTO> anonymousBoardCommentList = qryAnonymousBoardCommentMapper.findAllAnonymousBoardComment(anonymousBoardId, pageable);
        long total = qryAnonymousBoardCommentMapper.countAnonymousBoardComment(anonymousBoardId);
        if (anonymousBoardCommentList.isEmpty()) {
            throw new NoSuchElementException("Anonymous board comments not found for anonymousBoardId: " + anonymousBoardId);
        }
        return new PageImpl<>(anonymousBoardCommentList, pageable, total);
    }

    @Override
    public long countAnonymousBoardComment(int anonymousBoardId) {
        return qryAnonymousBoardCommentMapper.countAnonymousBoardComment(anonymousBoardId);
    }
}