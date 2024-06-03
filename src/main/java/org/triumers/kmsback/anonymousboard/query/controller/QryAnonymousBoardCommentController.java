package org.triumers.kmsback.anonymousboard.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.query.service.QryAnonymousBoardCommentService;

import java.util.NoSuchElementException;

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
            @PageableDefault(size = 10) Pageable pageable) {
        Page<QryAnonymousBoardCommentDTO> anonymousBoardCommentList = qryAnonymousBoardCommentService.findAllAnonymousBoardComment(anonymousBoardId, pageable);
        return ResponseEntity.ok(anonymousBoardCommentList);
    }

    // 댓글 개수 조회
    @GetMapping("/count")
    public ResponseEntity<Long> getAnonymousBoardCommentCount(@PathVariable int anonymousBoardId) {
        long commentCount = qryAnonymousBoardCommentService.countAnonymousBoardComment(anonymousBoardId);
        return ResponseEntity.ok(commentCount);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}