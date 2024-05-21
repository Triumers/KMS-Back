package org.triumers.kmsback.common.ai.service;

public interface OpenAIService {

    String requestToGPT(String prompt, String content);
}
