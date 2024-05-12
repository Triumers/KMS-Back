package org.triumers.kmsback.anonymousboard.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.query.service.QryAnonymousBoardService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/anonymous-board")
public class QryAnonymousBoardController {

    private final QryAnonymousBoardService qryAnonymousBoardService;

    @Autowired
    public QryAnonymousBoardController(QryAnonymousBoardService qryAnonymousBoardService) {
        this.qryAnonymousBoardService = qryAnonymousBoardService;
    }

    @GetMapping
    public ResponseEntity<Page<QryAnonymousBoardDTO>> searchAnonymousBoard(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword,
            Pageable pageable) {
        Page<QryAnonymousBoardDTO> anonymousBoardList;
        if (type == null || keyword == null) {
            anonymousBoardList = qryAnonymousBoardService.findAllAnonymousBoard(pageable);
        } else {
            anonymousBoardList = qryAnonymousBoardService.searchAnonymousBoard(type, keyword, pageable);
        }
        return ResponseEntity.ok(anonymousBoardList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QryAnonymousBoardDTO> getAnonymousBoardById(@PathVariable int id) {
        QryAnonymousBoardDTO anonymousBoard = qryAnonymousBoardService.findAnonymousBoardById(id);
        return ResponseEntity.ok(anonymousBoard);
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