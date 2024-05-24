package org.triumers.kmsback.common.translation.service;

import org.springframework.stereotype.Service;
import org.triumers.kmsback.common.translation.dto.PostDTO;
import org.triumers.kmsback.common.translation.entity.Post;
import org.triumers.kmsback.common.translation.entity.PostCn;
import org.triumers.kmsback.common.translation.entity.PostEng;
import org.triumers.kmsback.common.translation.entity.PostJpn;
import org.triumers.kmsback.common.translation.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final DeeplService deeplService;

    public PostServiceImpl(PostRepository postRepository, DeeplService deeplService) {
        this.postRepository = postRepository;
        this.deeplService = deeplService;
    }

    public PostDTO getTranslatedPost(int postId, String targetLang) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postId));

        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());

        if (targetLang.equals("en-US")) {
            if (post.getPostEng() == null) {
                String translatedTitle = deeplService.translate(post.getTitle(), "en-US");
                String translatedContent = deeplService.translate(post.getContent(), "en-US");
                post.setPostEng(new PostEng(post.getId(), translatedTitle, translatedContent, post));
                postRepository.save(post);
            }
            postDTO.setTitle(post.getPostEng().getTitle());
            postDTO.setContent(post.getPostEng().getContent());
        } else if (targetLang.equals("zh")) {
            if (post.getPostCn() == null) {
                String translatedTitle = deeplService.translate(post.getTitle(), "zh");
                String translatedContent = deeplService.translate(post.getContent(), "zh");
                post.setPostCn(new PostCn(post.getId(), translatedTitle, translatedContent, post));
                postRepository.save(post);
            }
            postDTO.setTitle(post.getPostCn().getTitle());
            postDTO.setContent(post.getPostCn().getContent());
        } else if (targetLang.equals("ja")) {
            if (post.getPostJpn() == null) {
                String translatedTitle = deeplService.translate(post.getTitle(), "ja");
                String translatedContent = deeplService.translate(post.getContent(), "ja");
                post.setPostJpn(new PostJpn(post.getId(), translatedTitle, translatedContent, post));
                postRepository.save(post);
            }
            postDTO.setTitle(post.getPostJpn().getTitle());
            postDTO.setContent(post.getPostJpn().getContent());
        }

        return postDTO;
    }
}
