package org.triumers.kmsback.quiz.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.quiz.command.Application.dto.CmdQuizDTO;
import org.triumers.kmsback.quiz.command.Application.service.CmdQuizService;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestQuizVo;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestRemoveQuizVo;

@RestController
@RequestMapping("/quiz")
public class CmdQuizController {

    private final CmdQuizService cmdQuizService;

    @Autowired
    public CmdQuizController(CmdQuizService cmdQuizService) {
        this.cmdQuizService = cmdQuizService;
    }

    /* 설명. 퀴즈 등록 */
    @PostMapping("/regist")
    public ResponseEntity<CmdQuizDTO> addQuiz(@RequestBody CmdRequestQuizVo request) {

        try {
            CmdQuizDTO registQuiz = cmdQuizService.registQuiz(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(registQuiz);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /* 설명. 퀴즈 수정 */
    @PutMapping("/edit")
    public ResponseEntity<CmdQuizDTO> editQuiz(@RequestBody CmdRequestQuizVo request) {

        try {
            CmdQuizDTO editQuiz = cmdQuizService.editQuiz(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(editQuiz);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /* 설명. 퀴즈 삭제 */
    @DeleteMapping("/remove")
    public ResponseEntity<CmdQuizDTO> deleteQuiz(@RequestBody CmdRequestRemoveQuizVo request) {

        try {
            cmdQuizService.removeQuiz(request.getId());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

