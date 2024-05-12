package org.triumers.kmsback.anonymousboard.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.command.Application.service.CmdAnonymousBoardService;

@RestController
@RequestMapping("/anonymous-board")
public class CmdAnonymousBoardController {

    private final CmdAnonymousBoardService cmdAnonymousBoardService;

    @Autowired
    public CmdAnonymousBoardController(CmdAnonymousBoardService cmdAnonymousBoardService) {
        this.cmdAnonymousBoardService = cmdAnonymousBoardService;
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<CmdAnonymousBoardDTO> createAnonymousBoard(@RequestBody CmdAnonymousBoardDTO cmdAnonymousBoardDTO) {
        CmdAnonymousBoardDTO savedAnonymousBoard = cmdAnonymousBoardService.saveAnonymousBoard(cmdAnonymousBoardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnonymousBoard);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnonymousBoard(@PathVariable int id) {
        cmdAnonymousBoardService.deleteAnonymousBoard(id);
        return ResponseEntity.noContent().build();
    }


}
