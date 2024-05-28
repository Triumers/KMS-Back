package org.triumers.kmsback.common.ai.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.triumers.kmsback.common.ai.dto.ChatGPTRequestDTO;
import org.triumers.kmsback.common.ai.dto.ChatGPTResponseDTO;

import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final Map<String, String> openAIEnv = new HashMap<>();

    private RestTemplate template;

    public OpenAIServiceImpl(Environment env, RestTemplate template) {
        this.template = template;
        openAIEnv.put("model", env.getProperty("open-ai.model"));
        openAIEnv.put("url", env.getProperty("open-ai.url"));
    }

    public ChatGPTResponseDTO requestToGPT(String prompt){
        String model = openAIEnv.get("model");
        String apiURL = openAIEnv.get("url");

        ChatGPTRequestDTO request = new ChatGPTRequestDTO(model, prompt);

        return template.postForObject(apiURL, request, ChatGPTResponseDTO.class);
    }

}
