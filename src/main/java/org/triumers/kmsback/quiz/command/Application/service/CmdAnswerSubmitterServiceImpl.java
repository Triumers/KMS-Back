package org.triumers.kmsback.quiz.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.quiz.command.Application.dto.CmdAnswerSubmitterDTO;
import org.triumers.kmsback.quiz.command.domain.aggregate.entity.CmdAnswerSubmitter;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestAnswerSubmitVo;
import org.triumers.kmsback.quiz.command.domain.repository.CmdAnswerSubmitterRepository;

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
            return toDto(submitAnswer);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving answer", e);
        }
    }
}
