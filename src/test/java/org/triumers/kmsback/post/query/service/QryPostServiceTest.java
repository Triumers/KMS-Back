package org.triumers.kmsback.post.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.employee.command.Application.dto.CmdEmployeeDTO;
import org.triumers.kmsback.employee.query.dto.QryEmployeeDTO;
import org.triumers.kmsback.post.query.aggregate.entity.QryLike;
import org.triumers.kmsback.post.query.aggregate.entity.QryPostAndTag;
import org.triumers.kmsback.post.query.aggregate.vo.QryRequestPost;
import org.triumers.kmsback.post.query.dto.QryLikeDTO;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QryPostServiceTest {

    private final QryPostService qryPostService;

    @Autowired
    QryPostServiceTest(QryPostService qryPostService) {
        this.qryPostService = qryPostService;
    }

    @Test
    @DisplayName("tab 게시글 리스트 조회")
    void findPostListByTab() {

        PageRequest pageRequest = PageRequest.of(0, 10);

        QryRequestPost request = new QryRequestPost(1, null);

        Page<QryPostAndTagsDTO> postList = qryPostService.findPostListByTab(request, pageRequest);
        
        assertFalse(postList.isEmpty());
    }

    @Test
    @DisplayName("단일 게시글 조회")
    void findPostById() {

        int postId = 1;
        QryPostAndTagsDTO post = qryPostService.findPostById(postId);

        assertThat(post.getId()).isNotNull();
    }

    @Test
    @DisplayName("게시글 히스토리 조회")
    void findHistoryListByOriginId() {

        int originId = 1;
        List<QryPostAndTagsDTO> history = qryPostService.findHistoryListByOriginId(originId);
        
        assertFalse(history.isEmpty());
    }

    @Test
    @DisplayName("게시글 좋아요 리스트 조회")
    void findLikeListByPostId() {

        int postId = 1;
        List<CmdEmployeeDTO> likeList = qryPostService.findLikeListByPostId(postId);

        // EMPLOYEE SERVICE 구현 완료되면 수정
        assertThat(likeList).isNotNull();
    }

}