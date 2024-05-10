package org.triumers.kmsback.post.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.kmsback.post.command.Application.dto.CmdPostAndTagsDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdTagDTO;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPost;
import org.triumers.kmsback.post.query.aggregate.entity.QryPostAndTag;

import java.time.LocalDate;
import java.util.ArrayList;
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
    void getPostListByTab() {

        int tabId = 1;
        List<QryPostAndTag> postList = qryPostService.getPostListByTab(tabId);

    }
}