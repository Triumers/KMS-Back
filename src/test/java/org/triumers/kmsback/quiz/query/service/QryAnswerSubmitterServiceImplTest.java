package org.triumers.kmsback.quiz.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.quiz.query.dto.QryAnswerSubmitterDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QryAnswerSubmitterServiceImplTest {

    @Autowired
    private QryAnswerSubmitterService  qryAnswerSubmitterService;

    @DisplayName("문제 ID로 문제 제출자 조회")
    @Test
    void findSubmitterByQuizId() {
        List<QryAnswerSubmitterDTO> qryAnswerSubmitterDTOs = qryAnswerSubmitterService.findSubmitterByQuizId(1);
        assertEquals(2, qryAnswerSubmitterDTOs.size());
    }
}