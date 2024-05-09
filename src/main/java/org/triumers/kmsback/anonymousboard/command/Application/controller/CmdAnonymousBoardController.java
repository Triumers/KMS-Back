package org.triumers.kmsback.anonymousboard.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.command.Application.service.CmdAnonymousBoardService;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoard;

@RestController
@RequestMapping("/anonymous-board")
public class CmdAnonymousBoardController {

    private final CmdAnonymousBoardService cmdAnonymousBoardService;

    @Autowired
    public CmdAnonymousBoardController(CmdAnonymousBoardService cmdAnonymousBoardService) {
        this.cmdAnonymousBoardService = cmdAnonymousBoardService;
    }

    // 1. 게시글 조회(페이징 처리까지)
    @GetMapping
    public ResponseEntity<Page<CmdAnonymousBoardDTO>> getAnonymousBoardList(Pageable pageable) {
        Page<CmdAnonymousBoardDTO> anonymousBoardList = cmdAnonymousBoardService.findAllAnonymousBoard(pageable);
        return ResponseEntity.ok(anonymousBoardList);
    }


}
