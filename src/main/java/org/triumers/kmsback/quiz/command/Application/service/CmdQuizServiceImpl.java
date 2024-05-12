package org.triumers.kmsback.quiz.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.quiz.command.Application.dto.CmdQuizDTO;
import org.triumers.kmsback.quiz.command.domain.aggregate.entity.CmdQuiz;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestQuizVo;
import org.triumers.kmsback.quiz.command.domain.repository.CmdQuizRepository;
import org.triumers.kmsback.quiz.query.aggregate.entity.QryQuiz;

import java.util.Optional;

@Service
public class CmdQuizServiceImpl implements CmdQuizService {

    private final CmdQuizRepository cmdQuizRepository;


    @Autowired
    public CmdQuizServiceImpl(CmdQuizRepository cmdQuizRepository) {
        this.cmdQuizRepository = cmdQuizRepository;
    }

    // Entity to DTO
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

    @Override
    public CmdQuizDTO editQuiz(CmdRequestQuizVo request) {
        // 퀴즈 ID를 조회
        Optional<CmdQuiz> quizOptional = cmdQuizRepository.findById(Long.valueOf(request.getId()));
        // 존재하는 경우
        if (quizOptional.isPresent()) {
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
                CmdQuiz editedQuiz = cmdQuizRepository.save(cmdQuiz);
                return toDto(editedQuiz);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error while saving quiz", e);
            }
        } else { // 퀴즈가 존재하지 않는 경우 예외 던지기
            throw new RuntimeException("Quiz with id " + request.getId() + " not found");
        }
    }

    @Override
    public CmdQuizDTO removeQuiz(int id) {
        // 퀴즈 ID를 조회
        Optional<CmdQuiz> quizOptional = cmdQuizRepository.findById(Long.valueOf(id));
        // 존재하는 경우
        if (quizOptional.isPresent()) {
            CmdQuiz cmdQuiz = quizOptional.get();
            try {
                cmdQuizRepository.delete(cmdQuiz);
                return toDto(cmdQuiz);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error while removing quiz", e);
            }
        } else {
            throw new RuntimeException("Quiz with id " + id + " not found");
        }
    }
}
