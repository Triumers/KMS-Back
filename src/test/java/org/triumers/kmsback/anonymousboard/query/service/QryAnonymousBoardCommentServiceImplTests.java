package org.triumers.kmsback.anonymousboard.query.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.query.repository.QryAnonymousBoardCommentMapper;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QryAnonymousBoardCommentServiceImplTests {

    @Mock
    private QryAnonymousBoardCommentMapper qryAnonymousBoardCommentMapper;

    @InjectMocks
    private QryAnonymousBoardCommentServiceImpl qryAnonymousBoardCommentService;

    @Test
    void findAllAnonymousBoardComment() {
        // given
        int anonymousBoardId = 1;
        List<QryAnonymousBoardCommentDTO> anonymousBoardCommentList = new ArrayList<>();
        anonymousBoardCommentList.add(new QryAnonymousBoardCommentDTO());
        PageRequest pageRequest = PageRequest.of(0, 10);
        given(qryAnonymousBoardCommentMapper.findAllAnonymousBoardComment(eq(anonymousBoardId), eq(pageRequest))).willReturn(anonymousBoardCommentList);
        given(qryAnonymousBoardCommentMapper.countAnonymousBoardComment(anonymousBoardId)).willReturn(1L);

        // when
        Page<QryAnonymousBoardCommentDTO> result = qryAnonymousBoardCommentService.findAllAnonymousBoardComment(anonymousBoardId, pageRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(anonymousBoardCommentList);
        assertThat(result.getTotalElements()).isEqualTo(1L);
    }
}