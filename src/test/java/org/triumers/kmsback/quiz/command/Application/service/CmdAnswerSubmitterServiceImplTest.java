package org.triumers.kmsback.quiz.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.quiz.command.Application.dto.CmdAnswerSubmitterDTO;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestAnswerSubmitVo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CmdAnswerSubmitterServiceImplTest {

    @Autowired
    private CmdAnswerSubmitterService cmdAnswerSubmitterService;

    @DisplayName("정답 제출 테스트")
    @Test
    void submitAnswer() {
        // given
        CmdRequestAnswerSubmitVo answer= new CmdRequestAnswerSubmitVo(1, "테스트 답변", "테스트 참조", true, 1, 1);

        // when
        assertDoesNotThrow(() -> cmdAnswerSubmitterService.submitAnswer(answer));

        // then
        assertEquals(1, answer.getId());
        assertEquals("테스트 답변", answer.getAnswer());
        assertEquals("테스트 참조", answer.getCommentary());
        assertEquals(true, answer.isStatus());
        assertEquals(1, answer.getQuizId());
        assertEquals(1, answer.getEmployeeId());
    }

    @DisplayName("정답 수정 테스트")
    @Test
    void editAnswer() {
        // given
        CmdRequestAnswerSubmitVo answer = new CmdRequestAnswerSubmitVo(1, "수정된 답변", "수정된 참조", false, 1, 1);

        // when
        assertDoesNotThrow(() -> cmdAnswerSubmitterService.submitAnswer(answer));

        // then
        assertEquals(1, answer.getId());
        assertEquals("수정된 답변", answer.getAnswer());
        assertEquals("수정된 참조", answer.getCommentary());
        assertEquals(false, answer.isStatus());
        assertEquals(1, answer.getQuizId());
        assertEquals(1, answer.getEmployeeId());
    }

    @DisplayName("정답 삭제 테스트")
    @Test
    void removeAnswer() {
        // given, when
        CmdAnswerSubmitterDTO deletedAnswer = cmdAnswerSubmitterService.removeAnswer(1);

        // then
        assertThat(deletedAnswer.getDeletedAt()).isNotNull();
    }
}