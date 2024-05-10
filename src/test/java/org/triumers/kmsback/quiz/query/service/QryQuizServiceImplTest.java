package org.triumers.kmsback.quiz.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.quiz.query.dto.QryQuizDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QryQuizServiceImplTest {

    @Autowired
    private QryQuizServiceImpl qryQuizService;

    @DisplayName("퀴즈 상태에 따른 퀴즈 조회")
    @Test
    void findQuizByStatus() {

        List<QryQuizDTO> qryQuizDTOs = qryQuizService.findQuizByStatus(false);
        assertNotNull(qryQuizDTOs);
        assertFalse(qryQuizDTOs.isEmpty());

    }

    @DisplayName("퀴즈 ID로 퀴즈 조회")
    @Test
    void findQuizById() {
        QryQuizDTO qryQuizDTO = qryQuizService.findQuizById(1);
        assertNotNull(qryQuizDTO);
    }
}