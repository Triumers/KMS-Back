package org.triumers.kmsback.user.query.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.common.LoggedInUser;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.post.command.Application.dto.CmdPostAndTagsDTO;
import org.triumers.kmsback.post.command.Application.service.CmdPostService;
import org.triumers.kmsback.user.command.Application.service.AuthService;
import org.triumers.kmsback.user.query.dto.QryDocsDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QryAuthServiceTest {

    @Autowired
    private QryAuthService qryAuthService;

    @Autowired
    private AuthService authService;

    @Autowired
    private CmdPostService cmdPostService;

    @Autowired
    private LoggedInUser loggedInUser;

    @BeforeEach
    void setUp() {
        loggedInUser.setting();
    }

    @DisplayName("내가 작성한 게시글 조회 테스트")
    @Test
    void findMyPost() throws NotLoginException {

        // given
        CmdPostAndTagsDTO post = createTestPost("new");
        cmdPostService.registPost(post);

        // when
        QryDocsDTO myPost = qryAuthService.findMyPost();

        // then
        assertNotNull(myPost);
        assertEquals(1, myPost.getDocsInfoList().size());
    }

    @Test
    void findMyComment() {
    }

    @Test
    void findMyLike() {
    }

    @Test
    void findMyBookmark() {
    }

    private CmdPostAndTagsDTO createTestPost(String type){
        List<String> tags = new ArrayList<>();
        tags.add("개발");
        tags.add("tag1");
        tags.add("tag2");
        tags.add("tag3");
        tags.add("tag4");

        return new CmdPostAndTagsDTO(type + "Title",  type + "Content", null, LocalDateTime.now(), 1, 1, tags);
    }
}