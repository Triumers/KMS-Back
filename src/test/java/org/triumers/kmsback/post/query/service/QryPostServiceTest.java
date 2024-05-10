package org.triumers.kmsback.post.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.kmsback.post.query.aggregate.entity.QryPostAndTag;
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

        int tabId = 1;
        List<QryPostAndTagsDTO> postList = qryPostService.findPostListByTab(tabId);
        System.out.println("postList = " + postList);
        assertFalse(postList.isEmpty());
    }

    @Test
    @DisplayName("게시글 조회")
    void findPostById() {

        int postId = 20;
        QryPostAndTagsDTO post = qryPostService.findPostById(postId);
        System.out.println("post = " + post);
        assertThat(post.getId()).isNotNull();
    }


}