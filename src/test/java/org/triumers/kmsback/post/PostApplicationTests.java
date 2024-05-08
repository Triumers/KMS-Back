package org.triumers.kmsback.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.kmsback.post.command.Application.dto.CmdPostDTO;
import org.triumers.kmsback.post.command.Application.service.CmdPostService;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdPost;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostApplicationTests {

    private final CmdPostService cmdPostService;

    @Autowired
    public PostApplicationTests(CmdPostService cmdPostService) {
        this.cmdPostService = cmdPostService;
    }

    @Test
    @DisplayName("게시글 저장")
    void registPost(){

        CmdPostDTO post = new CmdPostDTO("newTitle", "newContent", LocalDate.now(), 1, 1);

        CmdPost savedPost = cmdPostService.registPost(post);

        assertThat(savedPost.getId()).isNotNull();
    }

}
