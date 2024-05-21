package org.triumers.kmsback.common.ai;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.triumers.kmsback.post.command.Application.dto.ChatGPTRequestDTO;
import org.triumers.kmsback.post.command.Application.dto.ChatGPTResponseDTO;

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

    @Override
    public String requestToGPT(String type, String content){
        String model = openAIEnv.get("model");
        String apiURL = openAIEnv.get("url");

        String prompt = getPromptByType(type, content);

        ChatGPTRequestDTO request = new ChatGPTRequestDTO(model, prompt);
        ChatGPTResponseDTO chatGPTResponse =  template.postForObject(apiURL, request, ChatGPTResponseDTO.class);

        System.out.println("chatGPTResponse = " + chatGPTResponse);

        return "testGPT";
    }

    public String contentEnhancement(String content){
        String prompt =
                " 우리는 이제 글을 업그레이드 할 것이다." +
                        "다음 { } 안에 들어간 내용이 우리가 업그레이드 해야하는 글이다. "+
                        "{ "+
                        content
                        + "{ } 안에 들어간 글의 태그 안에 있는 내용들을 업그레이드 해서 다시 { } 안에 넣어서 반환해야 한다.";
        return prompt;
    }

    public String contentValidation(String content){
        String prompt =
                " 우리는 이제 글에 대한 내용을 검증할 것이다." +
                        "다음 { } 안에 들어간 내용이 우리가 검증해야하는 글이다. "+
                        "{ "+
                        content
                        + "{ } 안에 들어간 글의 내용들이 지식적으로 맞는지 검증하고 틀린 내용이 있다면 해당하는 부분도 함께 정리해서" +
                        " 다시 { } 안에 넣어서 반환해야 한다.";
        return prompt;
    }

    public String grammarCheck(String content){
        String prompt =
                " 우리는 이제 글의 맞춤법을 수정 할 것이다." +
                        "다음 { } 안에 들어간 내용이 우리가 수정해야 하는 글이다. "+
                        "{ "+
                        content
                        + "{ } 안에 들어간 글의 태그 안에 있는 내용들의 맞춤법을 수정해서 다시 { } 안에 넣어서 반환해야 한다.";
        return prompt;
    }

    public String search(String content){
        String prompt = content;

        return prompt;
    }

    String getPromptByType(String type, String content){

        switch (type){
            case "enhancement":
                return contentEnhancement(content);
            case "validation":
                return contentValidation(content);
            case "grammar":
                return grammarCheck(content);
            case "search":
                return search(content);
        }

        return content;
    }

}
