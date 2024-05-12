package org.triumers.kmsback.anonymousboard.query.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.query.service.QryAnonymousBoardCommentService;

@RestController
@RequestMapping("/anonymous-board/{anonymousBoardId}/comments")
public class QryAnonymousBoardCommentController {

    private final QryAnonymousBoardCommentService qryAnonymousBoardCommentService;

    @Autowired
    public QryAnonymousBoardCommentController(QryAnonymousBoardCommentService qryAnonymousBoardCommentService) {
        this.qryAnonymousBoardCommentService = qryAnonymousBoardCommentService;
    }

    // 댓글 목록 조회
    @GetMapping
    public ResponseEntity<Page<QryAnonymousBoardCommentDTO>> getAnonymousBoardCommentList(
            @PathVariable int anonymousBoardId,
            Pageable pageable) {
        Page<QryAnonymousBoardCommentDTO> anonymousBoardCommentList = qryAnonymousBoardCommentService.findAllAnonymousBoardComment(anonymousBoardId, pageable);
        return ResponseEntity.ok(anonymousBoardCommentList);
    }
}