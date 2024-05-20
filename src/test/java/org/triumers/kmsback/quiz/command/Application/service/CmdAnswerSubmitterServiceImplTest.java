package org.triumers.kmsback.quiz.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestAnswerSubmitVo;
import org.triumers.kmsback.quiz.command.domain.repository.CmdAnswerSubmitterRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CmdAnswerSubmitterServiceImplTest {

    @Autowired
    private CmdAnswerSubmitterService cmdAnswerSubmitterService;

    @Autowired
    private CmdAnswerSubmitterRepository cmdAnswerSubmitterRepository;

    @DisplayName("정답 제출 테스트")
    @Test
    void submitAnswer() {
        // given
        CmdRequestAnswerSubmitVo answer= new CmdRequestAnswerSubmitVo(1, "테스트 답변", "테스트 참조", true, 1, 1);

        // when, then
        assertDoesNotThrow(() -> cmdAnswerSubmitterService.submitAnswer(answer));
    }

    @DisplayName("정답 수정 테스트")
    @Test
    void editAnswer() {
        // given
        CmdRequestAnswerSubmitVo answer = new CmdRequestAnswerSubmitVo(1, "수정된 답변", "수정된 참조", false, 1, 1);

        // when, then
        assertDoesNotThrow(() -> cmdAnswerSubmitterService.submitAnswer(answer));
    }
}