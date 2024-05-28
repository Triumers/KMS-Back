package org.triumers.kmsback.common.translation.service;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DeeplServiceImpl implements DeeplService {

    private static final Logger logger = LoggerFactory.getLogger(DeeplServiceImpl.class);

    private final Translator translator;

    public DeeplServiceImpl(@Value("${deepl.auth-key}") String authKey) {
        this.translator = new Translator(authKey);
    }

    @Override
    public String translate(String text, String targetLang) {
        try {
            TextResult result = translator.translateText(text, null, targetLang);
            return result.getText();
        } catch (DeepLException e) {
            logger.error("DeepL API error: {}", e.getMessage());
            if (e.getCause() instanceof IOException) {
                // 네트워크 오류인 경우 재시도 로직 추가
                logger.info("Retrying translation due to network error...");
                return retryTranslate(text, targetLang, 3); // 최대 3회 재시도
            }
            return "Translation failed: " + e.getMessage();
        } catch (InterruptedException e) {
            logger.error("Translation interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
            return "Translation interrupted";
        }
    }

    private String retryTranslate(String text, String targetLang, int maxRetries) {
        int retryCount = 0;
        while (retryCount < maxRetries) {
            try {
                TextResult result = translator.translateText(text, null, targetLang);
                return result.getText();
            } catch (DeepLException | InterruptedException e) {
                retryCount++;
                logger.warn("Translation retry {} failed: {}", retryCount, e.getMessage());
                if (retryCount == maxRetries) {
                    logger.error("Max retries reached. Translation failed.");
                    return "Translation failed after " + maxRetries + " retries.";
                }
                try {
                    Thread.sleep(1000); // 1초 대기 후 재시도
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return "Translation interrupted during retry";
                }
            }
        }
        return "Translation failed after " + maxRetries + " retries.";
    }
}