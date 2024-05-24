package org.triumers.kmsback.quiz.query.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.quiz.query.dto.QryAnswerSubmitterDTO;
import org.triumers.kmsback.quiz.query.service.QryAnswerSubmitterService;

@RestController
@RequestMapping("/answer")
public class QryAnswerSubmitterController {
    private QryAnswerSubmitterService qryAnswerSubmitterService;

    public QryAnswerSubmitterController(QryAnswerSubmitterService qryAnswerSubmitterService) {
        this.qryAnswerSubmitterService = qryAnswerSubmitterService;
    }

    @GetMapping("/submitter")
    public List<QryAnswerSubmitterDTO> findQuizByIdAnswerSubmitter(int quizId) {
        List<QryAnswerSubmitterDTO> qryAnswerSubmitterDTOs = this.qryAnswerSubmitterService.findSubmitterByQuizId(quizId);
        return qryAnswerSubmitterDTOs;
    }

    @GetMapping("/count")
    public List<QryAnswerSubmitterDTO> findSubmitByEmployeeId(int employeeId) {
        List<QryAnswerSubmitterDTO> qryAnswerSubmitterDTOs = this.qryAnswerSubmitterService.findSubmitByEmployeeId(employeeId);
        return qryAnswerSubmitterDTOs;
    }
}
