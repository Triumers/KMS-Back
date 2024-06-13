package org.triumers.kmsback.common.translation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.common.translation.dto.PostDTO;
import org.triumers.kmsback.common.translation.service.PostService;

@RestController
@RequestMapping("/translate")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}/{targetLang}")
    public PostDTO translatePost(@PathVariable int postId, @PathVariable String targetLang) {
        try {
            return postService.getTranslatedPost(postId, targetLang);
        } catch (Exception e) {
            return null;
        }
    }
}
