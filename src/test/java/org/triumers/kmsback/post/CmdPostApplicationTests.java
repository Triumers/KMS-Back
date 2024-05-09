package org.triumers.kmsback.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.kmsback.post.command.Application.dto.CmdFavoritesDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdLikeDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdPostAndTagsDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdTagDTO;
import org.triumers.kmsback.post.command.Application.service.CmdPostService;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdFavorites;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdLike;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPost;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CmdPostApplicationTests {

    private final CmdPostService cmdPostService;

    @Autowired
    public CmdPostApplicationTests(CmdPostService cmdPostService) {
        this.cmdPostService = cmdPostService;
    }

    @Test
    @DisplayName("게시글 저장")
    void registPost() {

        List<CmdTagDTO> tags = new ArrayList<>();
        tags.add(new CmdTagDTO("tag1"));
        tags.add(new CmdTagDTO("tag2"));
        tags.add(new CmdTagDTO("tag3"));
        tags.add(new CmdTagDTO("tag4"));
        tags.add(new CmdTagDTO("tag5"));

        CmdPostAndTagsDTO post = new CmdPostAndTagsDTO("newTitle", "newContent", LocalDate.now(), 1, 1, tags);

        CmdPost savedPost = cmdPostService.registPost(post);

        assertThat(savedPost.getId()).isNotNull();
    }

    @Test
    @DisplayName("게시글 수정")
    void modifyPost() {

        List<CmdTagDTO> tags = new ArrayList<>();
        tags.add(new CmdTagDTO(4, "tag1"));
        tags.add(new CmdTagDTO(5, "tag2"));
        tags.add(new CmdTagDTO(6, "tag3"));
        tags.add(new CmdTagDTO("newtag4"));
        tags.add(new CmdTagDTO("newtag5"));

        CmdPostAndTagsDTO post = new CmdPostAndTagsDTO("modifyTitle", "modifyContent", LocalDate.now(),
                1, 1, 1, tags);

        CmdPost modifyPost = cmdPostService.modifyPost(post);

        assertThat(modifyPost.getRecentId()).isNotNull();
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost(){

        int postId = 2;
        CmdPost deletedPost = cmdPostService.deletePost(postId);

        assertThat(deletedPost.getDeletedAt()).isNotNull();
    }

    @Test
    @DisplayName("게시글 좋아요/삭제")
    void likePost() {

        CmdLikeDTO like = new CmdLikeDTO(1, 1);
        CmdLike likePost = cmdPostService.likePost(like);

        assertThat(likePost.getId()).isNotNull();
    }

    @Test
    @DisplayName("게시글 즐겨찾기/삭제")
    void favoritePost(){

        CmdFavoritesDTO favorite = new CmdFavoritesDTO(1, 1);
        CmdFavorites likePost = cmdPostService.favoritePost(favorite);

        assertThat(likePost.getId()).isNotNull();
    }


}
