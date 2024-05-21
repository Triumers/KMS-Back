package org.triumers.kmsback.common.ai;

public interface OpenAIService {

    String requestToGPT(String prompt, String content);
}
