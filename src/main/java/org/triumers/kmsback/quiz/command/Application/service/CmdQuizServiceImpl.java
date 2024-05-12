package org.triumers.kmsback.quiz.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.quiz.command.Application.dto.CmdQuizDTO;
import org.triumers.kmsback.quiz.command.domain.aggregate.entity.CmdQuiz;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestQuizVo;
import org.triumers.kmsback.quiz.command.domain.repository.CmdQuizRepository;

@Service
public class CmdQuizServiceImpl implements CmdQuizService {

    private final CmdQuizRepository cmdQuizRepository;


    @Autowired
    public CmdQuizServiceImpl(CmdQuizRepository cmdQuizRepository) {
        this.cmdQuizRepository = cmdQuizRepository;
    }

    private CmdQuizDTO toDto(CmdQuiz cmdQuiz) {
        CmdQuizDTO dto = new CmdQuizDTO();
        dto.setId(cmdQuiz.getId());
        dto.setContent(cmdQuiz.getContent());
        dto.setAnswer(cmdQuiz.getAnswer());
        dto.setCommentary(cmdQuiz.getCommentary());
        dto.setStatus(cmdQuiz.isStatus());
        dto.setQuestionerId(cmdQuiz.getQuestionerId());
        dto.setPostId(cmdQuiz.getPostId());
        dto.setTopTapId(cmdQuiz.getTopTapId());
        return dto;
    }

    /* 설명. 퀴즈 등록 */
    @Override
    public CmdQuizDTO registQuiz(CmdRequestQuizVo request) {
        try {
            CmdQuiz cmdQuiz = new CmdQuiz(
                    request.getId(),
                    request.getContent(),
                    request.getAnswer(),
                    request.getCommentary(),
                    request.isStatus(),
                    request.getQuestionerId(),
                    request.getPostId(),
                    request.getTopTapId()
            );
            CmdQuiz registQuiz = cmdQuizRepository.save(cmdQuiz);
            return toDto(registQuiz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving quiz", e);
        }

    }
}
