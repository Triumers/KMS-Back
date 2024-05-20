package org.triumers.kmsback.quiz.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.quiz.command.Application.dto.CmdAnswerSubmitterDTO;
import org.triumers.kmsback.quiz.command.Application.service.CmdAnswerSubmitterService;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestAnswerSubmitVo;

@RestController
@RequestMapping("/answer")
public class CmdAnswerSubmitterController {
    private CmdAnswerSubmitterService cmdAnswerSubmitterService;

    @Autowired
    public CmdAnswerSubmitterController(CmdAnswerSubmitterService cmdAnswerSubmitterService) {
        this.cmdAnswerSubmitterService = cmdAnswerSubmitterService;
    }

    /* 설명. 정답 제출 */
    @PostMapping("/submit")
    public ResponseEntity<CmdAnswerSubmitterDTO> submitAnswer(@RequestBody CmdRequestAnswerSubmitVo request) {
        CmdAnswerSubmitterDTO submitAnswer = cmdAnswerSubmitterService.submitAnswer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(submitAnswer);
    }

    /* 설명. 정답 수정 */

    /* 설명. 정답 삭제 */

}
