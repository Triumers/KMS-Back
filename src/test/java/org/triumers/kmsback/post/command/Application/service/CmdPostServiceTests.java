package org.triumers.kmsback.post.command.Application.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.kmsback.common.LoggedInUser;
import org.triumers.kmsback.common.ai.service.OpenAIService;
import org.triumers.kmsback.common.exception.NotAuthorizedException;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputTypeException;
import org.triumers.kmsback.post.command.Application.dto.*;
import org.triumers.kmsback.post.command.domain.aggregate.vo.CmdRequestPostAI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class CmdPostServiceTests {

    private final CmdPostService cmdPostService;
    private final OpenAIService openAIService;
    private final LoggedInUser loggedInUser;

    @Autowired
    public CmdPostServiceTests(CmdPostService cmdPostService, OpenAIService openAIService, LoggedInUser loggedInUser) {
        this.cmdPostService = cmdPostService;
        this.openAIService = openAIService;
        this.loggedInUser = loggedInUser;
    }

    @BeforeEach
    void loginSetting() throws WrongInputTypeException {
        loggedInUser.setting();
    }

    @Test
    @DisplayName("게시글 저장")
    void registPost() throws NotLoginException {

        CmdPostAndTagsDTO post = createTestPost("new");

        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(post);

        assertThat(savedPost.getId()).isNotNull();
    }

    @Test
    @DisplayName("게시글 수정")
    void modifyPost() throws NotLoginException {

        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(createTestPost("new"));

        List<String> modifyTags = new ArrayList<>();
        modifyTags.add("개발");
        modifyTags.add("tag1");
        modifyTags.add("tag2");
        modifyTags.add("tag3");
        modifyTags.add("tag4");

        CmdPostAndTagsDTO modifyPost = createTestPost("modify");
        modifyPost.setTags(modifyTags);
        modifyPost.setOriginId(savedPost.getId());

        cmdPostService.modifyPost(modifyPost);

        assertThat(modifyPost.getId()).isNotNull();
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() throws NotLoginException, NotAuthorizedException {

        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(createTestPost());
        CmdPostAndTagsDTO deletedPost = cmdPostService.deletePost(savedPost.getId());

        assertThat(deletedPost.getDeletedAt()).isNotNull();
    }

    @Test
    @DisplayName("게시글 좋아요/삭제")
    void likePost() throws NotLoginException {

        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(createTestPost());

        CmdLikeDTO like = new CmdLikeDTO(1, savedPost.getId());
        CmdLikeDTO likePost = cmdPostService.likePost(like);

        assertThat(likePost.getId()).isNotNull();
    }

    @Test
    @Order(4)
    @DisplayName("게시글 즐겨찾기/삭제")
    void favoritePost() throws NotLoginException {

        CmdPostAndTagsDTO savedPost = cmdPostService.registPost(createTestPost());

        CmdFavoritesDTO favorite = new CmdFavoritesDTO(1, savedPost.getId());
        CmdFavoritesDTO likePost = cmdPostService.favoritePost(favorite);

        assertThat(likePost.getId()).isNotNull();
    }

    void testGPT(){
        String testContent = "<h1>Spring Boot와 GPT API를 이용한 챗봇 구축</h1>\n" +
        "    \n" +
        "    <h2>소개</h2>\n" +
        "    <p>이번 게시글에서는 Spring Boot와 OpenAI의 GPT API를 활용하여 챗봇을 개발하는 방법에 대해 알아. 챗봇은 사용자의 질문에 데해 인공지능 기술을 활용하여 응답하는 프로그람입니다. 이 프로젝트를 통해 Spring Boot 애플리케이션에서 GPT 모델을 적용하는 방법을 배울 수 있습니다.</p>\n" +
        "    \n" +
        "    <h2>프로젝트 설정</h2>\n" +
        "    <p>먼저 Spring 이용하여 새로운 Spring Bot 프로젝트를 생성합니다. Web, Lombok, Spring Boot DevTools 의존성을 추가합니다. 그 후, 생성된 프로젝트에 OpenAI API 키를 설정합니다.</p>\n" +
        "\n" +
        "    <h2>서비스 직성</h2>\n" +
        "    <p>OpenAI API와 통신하는 서비스 클래스를 작성합니다. 이 클래스에서는 RestTemplate을 사용하여 OpenAI API에 요청을 보내고 응답을 받습니다.</p>\n" +
        "\n" +
        "    <h2>컨트롤러 작성</h2>\n" +
        "    <p>OpenAI API에 전달하고 응답을 반환  하는 컨트롤러를 작성합니다. 이 컨트롤러는 RESTful API 스타트포인트로서 사용됩니다.</p>\n" +
        "\n" +
        "    <h2>테스트</h2>\n" +
        "    <p>애플리케이션을 실행하고 API를 호출하여 제대로 작동  하는지 테스트합니다. Postman과 같은 도구를 사용하여 `t` 요청을 보내어 응답을 확인할 수 있습니다.</p>";

        CmdRequestPostAI request = new CmdRequestPostAI("enhancement", testContent);
        cmdPostService.requestToGPT(request);
    }

    private CmdPostAndTagsDTO createTestPost(){
        return createTestPost("");
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
