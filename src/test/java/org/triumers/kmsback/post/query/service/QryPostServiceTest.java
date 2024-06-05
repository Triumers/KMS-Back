package org.triumers.kmsback.post.query.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.triumers.kmsback.common.LoggedInUser;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputTypeException;
import org.triumers.kmsback.post.command.Application.dto.CmdLikeDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdFavoritesDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdPostAndTagsDTO;
import org.triumers.kmsback.post.command.Application.service.CmdPostService;
import org.triumers.kmsback.post.query.aggregate.vo.QryRequestPost;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class QryPostServiceTest {

    private final QryPostService qryPostService;
    private final CmdPostService cmdPostService;

    private final LoggedInUser loggedInUser;

    @Autowired
    QryPostServiceTest(QryPostService qryPostService, CmdPostService cmdPostService, LoggedInUser loggedInUser) {
        this.qryPostService = qryPostService;
        this.cmdPostService = cmdPostService;
        this.loggedInUser = loggedInUser;
    }

    @BeforeEach
    void loginSetting() throws WrongInputTypeException {
        loggedInUser.setting();
    }
    @Test
    @DisplayName("tab 게시글 리스트 조회")
    void findPostListByTab() throws NotLoginException {

        registPost();

        PageRequest pageRequest = PageRequest.of(0, 10);

        QryRequestPost request = new QryRequestPost(1, null);

        Page<QryPostAndTagsDTO> postList = qryPostService.findPostListByTab(request, pageRequest);

        assertFalse(postList.isEmpty());
    }

    @Test
    @DisplayName("회원이 속한 전체 게시글 리스트 조회")
    void findAllPostListByTab() throws NotLoginException {

        registPost();

        PageRequest pageRequest = PageRequest.of(0, 10);

        QryRequestPost request = new QryRequestPost(1, null);

        Page<QryPostAndTagsDTO> postList = qryPostService.findAllPostListByEmployee(request, pageRequest);

        assertFalse(postList.isEmpty());
    }

    @Test
    @DisplayName("단일 게시글 조회")
    void findPostById() throws NotLoginException {

        CmdPostAndTagsDTO post = registPost();
        QryPostAndTagsDTO selectedPost = qryPostService.findPostById(post.getId());

        assertThat(selectedPost.getId()).isNotNull();
    }

    @Test
    @DisplayName("사용자가 참여한 게시글 조회")
    void findPostByEmployeeId() throws NotLoginException {

        List<QryPostAndTagsDTO> selectedPost = qryPostService.findPostByEmployeeId(1);

        assertThat(selectedPost).isNotNull();
    }

    @Test
    @DisplayName("사용자가 좋아요한 게시글 조회")
    void findLikePostByEmployeeId() throws NotLoginException {

        CmdLikeDTO like = new CmdLikeDTO(1, 1);
        cmdPostService.likePost(like);

        List<QryPostAndTagsDTO> likedPost = qryPostService.findLikePostByEmployeeId(1);

        assertThat(likedPost).isNotNull();
    }

    @Test
    @DisplayName("게시글id로 사용자 좋아요 여부 조회")
    void isLikeByPostId() throws NotLoginException {

        CmdLikeDTO like = new CmdLikeDTO(1, 1);
        cmdPostService.likePost(like);

        Boolean isLiked = qryPostService.findIsLikedByPostId(1);

        assertThat(isLiked).isTrue();
    }

    @Test
    @DisplayName("게시글id로 사용자 즐겨찾기 여부 조회")
    void isFavoriteByPostId() throws NotLoginException {

        CmdFavoritesDTO favorite = new CmdFavoritesDTO(1, 1);
        cmdPostService.favoritePost(favorite);

        Boolean isFavorite = qryPostService.findIsFavoriteByPostId(1);

        assertThat(isFavorite).isTrue();
    }

    @Test
    @DisplayName("사용자가 즐겨찾기한 게시글 조회")
    void findFavoritePostByEmployeeId() throws NotLoginException {

        CmdFavoritesDTO favorite = new CmdFavoritesDTO(1, 1);
        cmdPostService.favoritePost(favorite);
        List<QryPostAndTagsDTO> favoritePost = qryPostService.findFavoritePostByEmployeeId(1);

        assertThat(favoritePost).isNotNull();
    }

    @Test
    @DisplayName("게시글 히스토리 조회")
    void findHistoryListByOriginId() throws NotLoginException {

        int originId = modifyPost();
        List<QryPostAndTagsDTO> history = qryPostService.findHistoryListByOriginId(originId);

        assertFalse(history.isEmpty());
    }

    @Test
    @DisplayName("게시글 좋아요 리스트 조회")
    void findLikeListByPostId() throws NotLoginException {

        CmdPostAndTagsDTO post = registPost();
        CmdFavoritesDTO favorite = new CmdFavoritesDTO(1, post.getId());
        cmdPostService.favoritePost(favorite);

        List<QryEmployeeDTO> likeList = qryPostService.findLikeListByPostId(post.getId());

        assertThat(likeList).isNotNull();
    }

    private CmdPostAndTagsDTO registPost() throws NotLoginException {

        CmdPostAndTagsDTO post = createTestPost();

        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(post);

        return  savedPost;
    }

    private Integer modifyPost() throws NotLoginException {
        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(createTestPost());

        CmdPostAndTagsDTO modifyPost = createTestPost();
        modifyPost.setOriginId(savedPost.getId());

        cmdPostService.modifyPost(modifyPost);

        return modifyPost.getOriginId();
    }

    private CmdPostAndTagsDTO createTestPost(){
        List<String> tags = new ArrayList<>();
        tags.add("개발");
        tags.add("tag1");
        tags.add("tag2");
        tags.add("tag3");
        tags.add("tag4");

        return new CmdPostAndTagsDTO("newTitle", "newContent", "imgurl", LocalDateTime.now(), 1, 1, tags);
    }
}