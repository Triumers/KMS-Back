package org.triumers.kmsback.post.command.Application.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.kmsback.post.command.Application.dto.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class CmdPostServiceTests {

    private final CmdPostService cmdPostService;

    @Autowired
    public CmdPostServiceTests(CmdPostService cmdPostService) {
        this.cmdPostService = cmdPostService;
    }

    @Test
    @Order(1)
    @DisplayName("게시글 저장")
    void registPost() {

        CmdPostAndTagsDTO post = createTestPost();

        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(post);

        assertThat(savedPost.getId()).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("게시글 수정")
    void modifyPost() {

        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(createTestPost());

        List<CmdTagDTO> tags = new ArrayList<>();
        tags.add(new CmdTagDTO("tag1"));
        tags.add(new CmdTagDTO("tag2"));
        tags.add(new CmdTagDTO("tag3"));
        tags.add(new CmdTagDTO("tag4"));
        tags.add(new CmdTagDTO("tag5"));

        CmdPostAndTagsDTO post = new CmdPostAndTagsDTO("modifyTitle", "modifyContent", LocalDateTime.now(),
                1, savedPost.getId(), 1, tags);

        CmdPostAndTagsDTO modifyPost = cmdPostService.modifyPost(post);

        assertThat(modifyPost.getId()).isNotNull();
    }

    @Test
    @Order(5)
    @DisplayName("게시글 삭제")
    void deletePost(){

        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(createTestPost());
        CmdPostAndTagsDTO deletedPost = cmdPostService.deletePost(savedPost.getId());

        assertThat(deletedPost.getDeletedAt()).isNotNull();
    }

    @Test
    @Order(3)
    @DisplayName("게시글 좋아요/삭제")
    void likePost() {

        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(createTestPost());

        CmdLikeDTO like = new CmdLikeDTO(1, savedPost.getId());
        CmdLikeDTO likePost = cmdPostService.likePost(like);

        assertThat(likePost.getId()).isNotNull();
    }

    @Test
    @Order(4)
    @DisplayName("게시글 즐겨찾기/삭제")
    void favoritePost(){

        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(createTestPost());

        CmdFavoritesDTO favorite = new CmdFavoritesDTO(1, savedPost.getId());
        CmdFavoritesDTO likePost = cmdPostService.favoritePost(favorite);

        assertThat(likePost.getId()).isNotNull();
    }

    private CmdPostAndTagsDTO createTestPost(){
        List<CmdTagDTO> tags = new ArrayList<>();
        tags.add(new CmdTagDTO("tag1"));
        tags.add(new CmdTagDTO("tag2"));
        tags.add(new CmdTagDTO("tag3"));
        tags.add(new CmdTagDTO("tag4"));
        tags.add(new CmdTagDTO("tag5"));

        return new CmdPostAndTagsDTO("newTitle", "newContent", LocalDateTime.now(), 1, 1, tags);
    }


}
