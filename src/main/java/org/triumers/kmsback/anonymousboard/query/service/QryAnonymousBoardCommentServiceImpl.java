package org.triumers.kmsback.anonymousboard.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.query.repository.QryAnonymousBoardCommentMapper;

import java.util.List;

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
        return new PageImpl<>(anonymousBoardCommentList, pageable, total);
    }
}