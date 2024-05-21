package org.triumers.kmsback.quiz.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.quiz.command.Application.dto.CmdAnswerSubmitterDTO;
import org.triumers.kmsback.quiz.command.domain.aggregate.entity.CmdAnswerSubmitter;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestAnswerSubmitVo;
import org.triumers.kmsback.quiz.command.domain.repository.CmdAnswerSubmitterRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CmdAnswerSubmitterServiceImpl implements CmdAnswerSubmitterService {

    private final CmdAnswerSubmitterRepository cmdAnswerSubmitterRepository;

    @Autowired
    public CmdAnswerSubmitterServiceImpl(CmdAnswerSubmitterRepository cmdAnswerSubmitterRepository) {
        this.cmdAnswerSubmitterRepository = cmdAnswerSubmitterRepository;
    }

    // Entity to DTO
    private CmdAnswerSubmitterDTO toDto(CmdAnswerSubmitter cmdAnswerSubmitter) {
        CmdAnswerSubmitterDTO dto = new CmdAnswerSubmitterDTO();
        dto.setId(cmdAnswerSubmitter.getId());
        dto.setAnswer(cmdAnswerSubmitter.getAnswer());
        dto.setCommentary(cmdAnswerSubmitter.getCommentary());
        dto.setStatus(cmdAnswerSubmitter.isStatus());
        dto.setQuizId(cmdAnswerSubmitter.getQuizId());
        dto.setEmployeeId(cmdAnswerSubmitter.getEmployeeId());
        dto.setDeletedAt(cmdAnswerSubmitter.getDeletedAt());
        return dto;
    }

    /* 설명. 정답 제출 */
    @Override
    public CmdAnswerSubmitterDTO submitAnswer(CmdRequestAnswerSubmitVo request) {
        try {
            CmdAnswerSubmitter cmdAnswerSubmitter = new CmdAnswerSubmitter(
                    request.getId(),
                    request.getAnswer(),
                    request.getCommentary(),
                    request.isStatus(),
                    request.getQuizId(),
                    request.getEmployeeId()
            );
            CmdAnswerSubmitter submitAnswer = cmdAnswerSubmitterRepository.saveAndFlush(cmdAnswerSubmitter);
            System.out.println("Answer Submitted: " + submitAnswer.getAnswer());
            return toDto(submitAnswer);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving answer", e);
        }
    }

    /* 설명. 정답 수정 */
    @Override
    public CmdAnswerSubmitterDTO editAnswer(CmdRequestAnswerSubmitVo request) {
        Optional<CmdAnswerSubmitter> answerOptional = cmdAnswerSubmitterRepository.findById(Long.valueOf(request.getId()));
        if (answerOptional.isPresent()) {
            try {
                CmdAnswerSubmitter cmdAnswerSubmitter = new CmdAnswerSubmitter(
                        request.getId(),
                        request.getAnswer(),
                        request.getCommentary(),
                        request.isStatus(),
                        request.getQuizId(),
                        request.getEmployeeId()
                );
                CmdAnswerSubmitter editAnswer = cmdAnswerSubmitterRepository.saveAndFlush(cmdAnswerSubmitter);
                System.out.println("Answer Edited: " + editAnswer.getAnswer());
                return toDto(editAnswer);
            }  catch (Exception e) {
                throw new RuntimeException("Error while updating answer", e);
            }
        } else {
            throw new RuntimeException("Answer not found");
        }
    }

    /* 설명. 정답 삭제 */
    @Override
    public CmdAnswerSubmitterDTO removeAnswer(int id) {
        Optional<CmdAnswerSubmitter> answerOptional = cmdAnswerSubmitterRepository.findById(Long.valueOf(id));
        if (answerOptional.isPresent()) {
            CmdAnswerSubmitter cmdAnswerSubmitter = answerOptional.get();
            cmdAnswerSubmitter.setDeletedAt(LocalDateTime.now());
            try {
                cmdAnswerSubmitterRepository.delete(cmdAnswerSubmitter);
                System.out.println("Answer Removed: " + cmdAnswerSubmitter.getAnswer());
                return toDto(cmdAnswerSubmitter);
            } catch (Exception e) {
                throw new RuntimeException("Error while deleting answer", e);
            }
        } else {
            throw new RuntimeException("Answer not found");
        }
    }


}
