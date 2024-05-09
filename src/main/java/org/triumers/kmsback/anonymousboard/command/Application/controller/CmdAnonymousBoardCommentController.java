package org.triumers.kmsback.anonymousboard.command.Application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.command.Application.service.CmdAnonymousBoardCommentService;

@RestController
@RequestMapping("/anonymous-board/{anonymousBoardId}/comments")
@RequiredArgsConstructor
public class CmdAnonymousBoardCommentController {

    private final CmdAnonymousBoardCommentService cmdAnonymousBoardCommentService;

    // 댓글 목록 조회
    @GetMapping
    public ResponseEntity<Page<CmdAnonymousBoardCommentDTO>> getAnonymousBoardCommentList(
            @PathVariable int anonymousBoardId,
            Pageable pageable) {
        Page<CmdAnonymousBoardCommentDTO> anonymousBoardCommentList = cmdAnonymousBoardCommentService.findAllAnonymousBoardComment(anonymousBoardId, pageable);
        return ResponseEntity.ok(anonymousBoardCommentList);
    }

    // 댓글 작성
    @PostMapping
    public ResponseEntity<CmdAnonymousBoardCommentDTO> createAnonymousBoardComment(
            @PathVariable int anonymousBoardId,
            @RequestBody CmdAnonymousBoardCommentDTO cmdAnonymousBoardCommentDTO) {
        cmdAnonymousBoardCommentDTO.setAnonymousBoardId(anonymousBoardId);
        CmdAnonymousBoardCommentDTO savedAnonymousBoardComment = cmdAnonymousBoardCommentService.saveAnonymousBoardComment(cmdAnonymousBoardCommentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnonymousBoardComment);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnonymousBoardComment(@PathVariable int id) {
        cmdAnonymousBoardCommentService.deleteAnonymousBoardComment(id);
        return ResponseEntity.noContent().build();
    }
}