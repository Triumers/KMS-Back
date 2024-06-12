package org.triumers.kmsback.quiz.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.quiz.command.Application.dto.CmdAnswerSubmitterDTO;
import org.triumers.kmsback.quiz.command.Application.service.CmdAnswerSubmitterService;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestAnswerSubmitVo;

@RestController
@RequestMapping("/answer")
public class CmdAnswerSubmitterController {

    private final CmdAnswerSubmitterService cmdAnswerSubmitterService;

    @Autowired
    public CmdAnswerSubmitterController(CmdAnswerSubmitterService cmdAnswerSubmitterService) {
        this.cmdAnswerSubmitterService = cmdAnswerSubmitterService;
    }

    /* 설명. 정답 제출 */
    @PostMapping("/submit")
    public ResponseEntity<CmdAnswerSubmitterDTO> submitAnswer(@RequestBody CmdRequestAnswerSubmitVo request) {
        try {
            CmdAnswerSubmitterDTO submitAnswer = cmdAnswerSubmitterService.submitAnswer(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(submitAnswer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /* 설명. 정답 수정 */
    @PutMapping("/edit")
    public ResponseEntity<CmdAnswerSubmitterDTO> editAnswer(@RequestBody CmdRequestAnswerSubmitVo request) {
        try {
            CmdAnswerSubmitterDTO editAnswer = cmdAnswerSubmitterService.editAnswer(request);
            return ResponseEntity.status(HttpStatus.OK).body(editAnswer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /* 설명. 정답 삭제 */
    @DeleteMapping("/remove")
    public ResponseEntity<CmdAnswerSubmitterDTO> removeAnswer(@RequestBody CmdRequestAnswerSubmitVo request) {
        try {
            cmdAnswerSubmitterService.removeAnswer(request.getId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
