package org.triumers.kmsback.quiz.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestQuizVo;
import org.triumers.kmsback.quiz.command.domain.repository.CmdQuizRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CmdQuizServiceImplTest {

    @Autowired
    private CmdQuizService cmdQuizService;

    @Autowired
    private CmdQuizRepository cmdQuizRepository;

    @DisplayName("퀴즈 등록 테스트")
    @Test
    void registQuiz() {
        // given
        CmdRequestQuizVo quiz = new CmdRequestQuizVo(1, "테스트 문제", "테스트 정답", "테스트 참고 자료", true, 1, 16, 2);

        // when, then
        assertDoesNotThrow(() -> cmdQuizService.registQuiz(quiz));
    }
}