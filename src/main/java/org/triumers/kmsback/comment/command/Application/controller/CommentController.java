package org.triumers.kmsback.comment.command.Application.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.comment.command.Application.dto.CmdCommentDTO;
import org.triumers.kmsback.comment.command.Application.service.CommentService;
import org.triumers.kmsback.common.exception.NotAuthorizedException;


@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CmdCommentDTO> addComment(@RequestBody CmdCommentDTO comment) {
        return ResponseEntity.ok(commentService.addComment(comment));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CmdCommentDTO> updateComment(@PathVariable Integer commentId, @RequestBody CmdCommentDTO comment) throws NotAuthorizedException {
        return ResponseEntity.ok(commentService.updateComment(commentId, comment));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId, @RequestParam Long userId, @RequestParam boolean isAdmin) throws NotAuthorizedException {
        commentService.deleteComment(commentId, userId, isAdmin);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<String> handleNotAuthorizedException(NotAuthorizedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
