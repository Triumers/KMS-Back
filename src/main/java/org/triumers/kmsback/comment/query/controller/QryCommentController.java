package org.triumers.kmsback.comment.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.comment.query.aggregate.entity.QryComment;
import org.triumers.kmsback.comment.query.domain.service.QryCommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class QryCommentController {

    @Autowired
    private QryCommentService qryCommentService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QryComment>> getCommentsByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(qryCommentService.getCommentsByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<QryComment>> getCommentsByPostId(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok(qryCommentService.getCommentsByPostId(postId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
