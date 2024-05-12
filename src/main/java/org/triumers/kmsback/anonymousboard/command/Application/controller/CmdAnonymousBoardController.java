package org.triumers.kmsback.anonymousboard.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.command.Application.service.CmdAnonymousBoardService;

import java.util.NoSuchElementException;

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
        if (cmdAnonymousBoardDTO.getTitle() == null || cmdAnonymousBoardDTO.getTitle().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        CmdAnonymousBoardDTO savedAnonymousBoard = cmdAnonymousBoardService.saveAnonymousBoard(cmdAnonymousBoardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnonymousBoard);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnonymousBoard(@PathVariable int id) {
        cmdAnonymousBoardService.deleteAnonymousBoard(id);
        return ResponseEntity.noContent().build();
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