package org.triumers.kmsback.quiz.command.Application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.quiz.command.Application.dto.CmdQuizDTO;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestQuizVo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CmdQuizServiceImplTest {

    @Autowired
    private CmdQuizService cmdQuizService;

    @DisplayName("퀴즈 등록 테스트")
    @Test
    void registQuiz() {
        // given
        CmdRequestQuizVo quiz = new CmdRequestQuizVo(1, "테스트 문제", "테스트 정답", "테스트 참고 자료", true, 1, 16, 2);

        // when
        assertDoesNotThrow(() -> cmdQuizService.registQuiz(quiz));

        // then
        assertEquals(1, quiz.getId());
        assertEquals("테스트 문제", quiz.getContent());
        assertEquals("테스트 정답", quiz.getAnswer());
        assertEquals("테스트 참고 자료", quiz.getCommentary());
        assertEquals(true, quiz.isStatus());
        assertEquals(1, quiz.getQuestionerId());
        assertEquals(16, quiz.getPostId());
        assertEquals(2, quiz.getTapId());
    }

    @DisplayName("퀴즈 수정 테스트")
    @Test
    void editQuiz() {
        // given
        CmdRequestQuizVo quiz = new CmdRequestQuizVo(1, "수정한 문제", "수정한 정답", "수정한 참고 자료", false, 5, 16, 2);

        // when
        assertDoesNotThrow(() -> cmdQuizService.editQuiz(quiz));

        // then
        assertEquals(1, quiz.getId());
        assertEquals("수정한 문제", quiz.getContent());
        assertEquals("수정한 정답", quiz.getAnswer());
        assertEquals("수정한 참고 자료", quiz.getCommentary());
        assertEquals(false, quiz.isStatus());
        assertEquals(5, quiz.getQuestionerId());
        assertEquals(16, quiz.getPostId());
        assertEquals(2, quiz.getTapId());
    }

    @DisplayName("퀴즈 삭제 테스트")
    @Test
    void removeQuiz() {
        // given, when
        CmdQuizDTO deletedQuiz = cmdQuizService.removeQuiz(1);

        // then
        assertThat(deletedQuiz.getDeletedAt()).isNotNull();
    }
}