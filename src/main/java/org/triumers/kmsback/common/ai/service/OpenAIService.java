package org.triumers.kmsback.common.ai.service;

import org.triumers.kmsback.common.ai.dto.ChatGPTResponseDTO;

public interface OpenAIService {

    ChatGPTResponseDTO requestToGPT(String prompt);
}
