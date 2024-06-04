package org.triumers.kmsback.quiz.query.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.quiz.query.dto.QryAnswerSubmitterDTO;
import org.triumers.kmsback.quiz.query.service.QryAnswerSubmitterService;

@RestController
@RequestMapping("/answer")
public class QryAnswerSubmitterController {
    private QryAnswerSubmitterService qryAnswerSubmitterService;

    public QryAnswerSubmitterController(QryAnswerSubmitterService qryAnswerSubmitterService) {
        this.qryAnswerSubmitterService = qryAnswerSubmitterService;
    }

    /* 설명. 문제 ID로 문제 답 제출자 조회 */
    @GetMapping("/submitter")
    public ResponseEntity<List<QryAnswerSubmitterDTO>> findQuizByIdAnswerSubmitter(@RequestParam int quizId) {
        List<QryAnswerSubmitterDTO> qryAnswerSubmitterDTOList = this.qryAnswerSubmitterService.findSubmitterByQuizId(quizId);
        return ResponseEntity.ok(qryAnswerSubmitterDTOList);
    }

    /* 설명. 사원 ID로 제출 내역 조회 */
    @GetMapping("/submit-list")
    public ResponseEntity<List<QryAnswerSubmitterDTO>> findSubmitByEmployeeId(@PathVariable int employeeId) {
        List<QryAnswerSubmitterDTO> qryAnswerSubmitterDTOList = this.qryAnswerSubmitterService.findSubmitByEmployeeId(employeeId);
        return ResponseEntity.ok(qryAnswerSubmitterDTOList);
    }
}
