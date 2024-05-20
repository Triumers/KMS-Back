package org.triumers.kmsback.post.command.Application.service;

public interface OpenAIService {

    String requestToGPT(String prompt, String content);
}
