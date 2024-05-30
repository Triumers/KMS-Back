package org.triumers.kmsback.comment.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.comment.command.Application.service.CommentServiceImpl;
import org.triumers.kmsback.comment.command.Domain.aggregate.entity.Comment;
import org.triumers.kmsback.comment.query.dto.CommentDTO;
import org.triumers.kmsback.common.translation.entity.Post;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getCommentByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestParam Integer postId, @RequestParam Long authorId, @RequestParam String content) {
        Comment comment = commentService.createComment(postId, authorId, content);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestParam String content) {
        Comment updatedComment = commentService.updateComment(commentId, content);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
