package org.triumers.kmsback.anonymousboard.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.command.Application.service.CmdAnonymousBoardCommentService;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoard;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardRepository;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/anonymous-board/{anonymousBoardId}/comments")
public class CmdAnonymousBoardCommentController {

    private final CmdAnonymousBoardCommentService cmdAnonymousBoardCommentService;

    private final CmdAnonymousBoardRepository cmdAnonymousBoardRepository;

    @Autowired
    public CmdAnonymousBoardCommentController(CmdAnonymousBoardCommentService cmdAnonymousBoardCommentService, CmdAnonymousBoardRepository cmdAnonymousBoardRepository) {
        this.cmdAnonymousBoardCommentService = cmdAnonymousBoardCommentService;
        this.cmdAnonymousBoardRepository = cmdAnonymousBoardRepository;
    }

    // 댓글 작성
    @PostMapping
    public ResponseEntity<CmdAnonymousBoardCommentDTO> createAnonymousBoardComment(
            @PathVariable int anonymousBoardId,
            @RequestBody CmdAnonymousBoardCommentDTO cmdAnonymousBoardCommentDTO) {

        try {
            CmdAnonymousBoard anonymousBoard = cmdAnonymousBoardRepository.findById(anonymousBoardId)
                    .orElseThrow(() -> new NoSuchElementException("Anonymous board not found with id: " + anonymousBoardId));

            cmdAnonymousBoardCommentDTO.setAnonymousBoard(anonymousBoard);
            CmdAnonymousBoardCommentDTO savedAnonymousBoardComment = cmdAnonymousBoardCommentService.saveAnonymousBoardComment(cmdAnonymousBoardCommentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAnonymousBoardComment);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnonymousBoardComment(@PathVariable int id) {

        try {
            cmdAnonymousBoardCommentService.deleteAnonymousBoardComment(id);
            return ResponseEntity.noContent().build();
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}