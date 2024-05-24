package org.triumers.kmsback.common.translation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.common.translation.dto.PostDTO;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Transactional
class PostServiceImplTests {

    @Autowired
    private PostService postService;

    @Test
    void getTranslatedPost_ValidInput_ReturnsTranslatedPostDTOInEnglish() {
        int postId = 1;
        String targetLang = "en-US";

        PostDTO postDTO = postService.getTranslatedPost(postId, targetLang);

        assertNotNull(postDTO);
        assertNotNull(postDTO.getTitle());
        assertNotNull(postDTO.getContent());
        System.out.println("Translated Title (English): " + postDTO.getTitle());
        System.out.println("Translated Content (English): " + postDTO.getContent());
    }

    @Test
    void getTranslatedPost_ValidInput_ReturnsTranslatedPostDTOInJapanese() {
        int postId = 1;
        String targetLang = "ja";

        PostDTO postDTO = postService.getTranslatedPost(postId, targetLang);

        assertNotNull(postDTO);
        assertNotNull(postDTO.getTitle());
        assertNotNull(postDTO.getContent());
        System.out.println("Translated Title (Japanese): " + postDTO.getTitle());
        System.out.println("Translated Content (Japanese): " + postDTO.getContent());
    }

    @Test
    void getTranslatedPost_ValidInput_ReturnsTranslatedPostDTOInChinese() {
        int postId = 1;
        String targetLang = "zh";

        PostDTO postDTO = postService.getTranslatedPost(postId, targetLang);

        assertNotNull(postDTO);
        assertNotNull(postDTO.getTitle());
        assertNotNull(postDTO.getContent());
        System.out.println("Translated Title (Chinese): " + postDTO.getTitle());
        System.out.println("Translated Content (Chinese): " + postDTO.getContent());
    }
}