package org.triumers.kmsback.anonymousboard.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.query.service.QryAnonymousBoardService;

@RestController
@RequestMapping("/anonymous-board")
public class QryAnonymousBoardController {

    private final QryAnonymousBoardService qryAnonymousBoardService;

    @Autowired
    public QryAnonymousBoardController(QryAnonymousBoardService qryAnonymousBoardService) {
        this.qryAnonymousBoardService = qryAnonymousBoardService;
    }

    @GetMapping
    public ResponseEntity<Page<QryAnonymousBoardDTO>> getAllAnonymousBoards(@PageableDefault(size = 10) Pageable pageable) {
        Page<QryAnonymousBoardDTO> anonymousBoardPage = qryAnonymousBoardService.findAllAnonymousBoard(pageable);
        return ResponseEntity.ok(anonymousBoardPage);
    }

    // 프론트에서 검색어 인코딩해서 보내야 함
    @GetMapping("/search")
    public ResponseEntity<Page<QryAnonymousBoardDTO>> searchAnonymousBoards(
            @RequestParam(value = "keyword", required = true) String keyword,
            @RequestParam(value = "type", required = false, defaultValue = "titleandcontent") String type,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<QryAnonymousBoardDTO> anonymousBoardPage = qryAnonymousBoardService.searchAnonymousBoard(keyword, type, pageable);
        return ResponseEntity.ok(anonymousBoardPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QryAnonymousBoardDTO> getAnonymousBoardById(@PathVariable int id) {
        QryAnonymousBoardDTO anonymousBoard = qryAnonymousBoardService.findAnonymousBoardById(id);
        return ResponseEntity.ok(anonymousBoard);
    }
}