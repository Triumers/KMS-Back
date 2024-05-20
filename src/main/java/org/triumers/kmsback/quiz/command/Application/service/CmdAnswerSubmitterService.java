package org.triumers.kmsback.quiz.command.Application.service;

import org.triumers.kmsback.quiz.command.Application.dto.CmdAnswerSubmitterDTO;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestAnswerSubmitVo;

public interface CmdAnswerSubmitterService {
    CmdAnswerSubmitterDTO submitAnswer(CmdRequestAnswerSubmitVo request);
}
