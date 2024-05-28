package org.triumers.kmsback.common.translation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.common.translation.entity.Post;
import org.triumers.kmsback.common.translation.repository.PostRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DeeplServiceImplTests {

    @Autowired
    private DeeplService deeplService;

    @Autowired
    private PostRepository postRepository;

    private Post post;

    @BeforeEach
    void setUp() {
        // 테스트용 게시물 생성
        post = new Post();
        post.setTitle("안녕하세요!");
        post.setContent("이것은 테스트 게시물입니다.");
        post.setAuthorId(1);
        postRepository.save(post);
    }

    @Test
    void translate_ValidInput_ReturnsTranslatedTextInEnglish() {
        // Arrange
        String targetLang = "en-US";

        // Act
        String translatedTitle = deeplService.translate(post.getTitle(), targetLang);
        String translatedContent = deeplService.translate(post.getContent(), targetLang);

        // Assert
        assertNotNull(translatedTitle);
        assertNotNull(translatedContent);
        System.out.println("Translated Title (English): " + translatedTitle);
        System.out.println("Translated Content (English): " + translatedContent);
    }

    @Test
    void translate_ValidInput_ReturnsTranslatedTextInJapanese() {
        // Arrange
        String targetLang = "ja";

        // Act
        String translatedTitle = deeplService.translate(post.getTitle(), targetLang);
        String translatedContent = deeplService.translate(post.getContent(), targetLang);

        // Assert
        assertNotNull(translatedTitle);
        assertNotNull(translatedContent);
        System.out.println("Translated Title (Japanese): " + translatedTitle);
        System.out.println("Translated Content (Japanese): " + translatedContent);
    }

    @Test
    void translate_ValidInput_ReturnsTranslatedTextInChinese() {
        // Arrange
        String targetLang = "zh";

        // Act
        String translatedTitle = deeplService.translate(post.getTitle(), targetLang);
        String translatedContent = deeplService.translate(post.getContent(), targetLang);

        // Assert
        assertNotNull(translatedTitle);
        assertNotNull(translatedContent);
        System.out.println("Translated Title (Chinese): " + translatedTitle);
        System.out.println("Translated Content (Chinese): " + translatedContent);
    }

}