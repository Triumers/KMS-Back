package org.triumers.kmsback.post.query.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.employee.command.Application.dto.CmdEmployeeDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdFavoritesDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdPostAndTagsDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdTagDTO;
import org.triumers.kmsback.post.command.Application.service.CmdPostService;
import org.triumers.kmsback.post.query.aggregate.vo.QryRequestPost;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;

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

    @Autowired
    QryPostServiceTest(QryPostService qryPostService, CmdPostService cmdPostService) {
        this.qryPostService = qryPostService;
        this.cmdPostService = cmdPostService;
    }

//    @Test
//    @DisplayName("tab 게시글 리스트 조회")
//    void findPostListByTab() throws NotLoginException {
//
//        registPost();
//
//        PageRequest pageRequest = PageRequest.of(0, 10);
//
//        QryRequestPost request = new QryRequestPost(1, null);
//
//        Page<QryPostAndTagsDTO> postList = qryPostService.findPostListByTab(request, pageRequest);
//
//        assertFalse(postList.isEmpty());
//    }
//
//    @Test
//    @DisplayName("단일 게시글 조회")
//    void findPostById() throws NotLoginException {
//
//        CmdPostAndTagsDTO post = registPost();
//        QryPostAndTagsDTO selectedPost = qryPostService.findPostById(post.getId());
//
//        assertThat(selectedPost.getId()).isNotNull();
//    }
//
//    @Test
//    @DisplayName("게시글 히스토리 조회")
//    void findHistoryListByOriginId() throws NotLoginException {
//
//        int originId = modifyPost();
//        List<QryPostAndTagsDTO> history = qryPostService.findHistoryListByOriginId(originId);
//
//        assertFalse(history.isEmpty());
//    }
//
//    @Test
//    @DisplayName("게시글 좋아요 리스트 조회")
//    void findLikeListByPostId() throws NotLoginException {
//
//        CmdPostAndTagsDTO post = registPost();
//        CmdFavoritesDTO favorite = new CmdFavoritesDTO(1, post.getId());
//        cmdPostService.favoritePost(favorite);
//
//        List<CmdEmployeeDTO> likeList = qryPostService.findLikeListByPostId(post.getId());
//
//        assertThat(likeList).isNotNull();
//    }
//
//    private CmdPostAndTagsDTO registPost() throws NotLoginException {
//
//        CmdPostAndTagsDTO post = createTestPost();
//
//        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(post);
//
//        return  savedPost;
//    }
//
//    private Integer modifyPost() throws NotLoginException {
//        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(createTestPost());
//
//        CmdPostAndTagsDTO modifyPost = createTestPost();
//        modifyPost.setOriginId(savedPost.getId());
//
//        cmdPostService.modifyPost(modifyPost);
//
//        return modifyPost.getOriginId();
//    }
//
//    private CmdPostAndTagsDTO createTestPost(){
//        List<String> tags = new ArrayList<>();
//        tags.add("개발");
//        tags.add("tag1");
//        tags.add("tag2");
//        tags.add("tag3");
//        tags.add("tag4");
//
//        return new CmdPostAndTagsDTO("newTitle", "newContent", LocalDateTime.now(), 1, 1, tags);
//    }
}