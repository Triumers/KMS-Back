package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoard;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardCommentRepository;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardRepository;
import org.triumers.kmsback.common.util.MacAddressUtil;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CmdAnonymousBoardCommentServiceImplTests {

    @Autowired
    private CmdAnonymousBoardCommentRepository cmdAnonymousBoardCommentRepository;

    @Autowired
    private CmdAnonymousBoardCommentService cmdAnonymousBoardCommentService;

    @Autowired
    private CmdAnonymousBoardRepository cmdAnonymousBoardRepository;

    private CmdAnonymousBoardCommentDTO cmdAnonymousBoardCommentDTO;
    private CmdAnonymousBoardDTO cmdAnonymousBoardDTO;
    private CmdAnonymousBoard savedAnonymousBoard;

    @BeforeEach
    void setUp() {
        cmdAnonymousBoardDTO = new CmdAnonymousBoardDTO();
        cmdAnonymousBoardDTO.setTitle("Test Board");
        cmdAnonymousBoardDTO.setContent("Test Board Content");

        savedAnonymousBoard = cmdAnonymousBoardRepository.save(new CmdAnonymousBoard(cmdAnonymousBoardDTO.getTitle(), cmdAnonymousBoardDTO.getContent(), "test_mac_address"));

        cmdAnonymousBoardCommentDTO = new CmdAnonymousBoardCommentDTO();
        cmdAnonymousBoardCommentDTO.setNickname("Test Nickname");
        cmdAnonymousBoardCommentDTO.setContent("Test Comment");
        cmdAnonymousBoardCommentDTO.setAnonymousBoard(savedAnonymousBoard);
    }

    @DisplayName("익명게시판 게시글 댓글 저장 성공 여부 테스트")
    @Test
    void saveAnonymousBoardComment_ValidInput_Success() {
        // when
        CmdAnonymousBoardCommentDTO savedCmdAnonymousBoardCommentDTO = cmdAnonymousBoardCommentService.saveAnonymousBoardComment(cmdAnonymousBoardCommentDTO);

        // then
        assertNotNull(savedCmdAnonymousBoardCommentDTO);
        assertEquals(cmdAnonymousBoardCommentDTO.getContent(), savedCmdAnonymousBoardCommentDTO.getContent());
        assertEquals(savedAnonymousBoard.getId(), savedCmdAnonymousBoardCommentDTO.getAnonymousBoard().getId());
        assertNotNull(savedCmdAnonymousBoardCommentDTO.getMacAddress());
    }

    @DisplayName("익명게시판 게시글 댓글 내용이 비어있는 경우 예외 발생 여부 테스트")
    @Test
    void saveAnonymousBoardComment_EmptyContent_ThrowsIllegalArgumentException() {
        // given
        cmdAnonymousBoardCommentDTO.setContent("");

        // when & then
        assertThrows(IllegalArgumentException.class, () -> cmdAnonymousBoardCommentService.saveAnonymousBoardComment(cmdAnonymousBoardCommentDTO));
    }

    @DisplayName("익명게시판 유효하지 않은 게시글의 댓글 작성 시 예외 발생 여부 테스트")
    @Test
    void saveAnonymousBoardComment_InvalidAnonymousBoard_ThrowsIllegalArgumentException() {
        // given
        CmdAnonymousBoard invalidAnonymousBoard = new CmdAnonymousBoard();
        cmdAnonymousBoardCommentDTO.setAnonymousBoard(invalidAnonymousBoard);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> cmdAnonymousBoardCommentService.saveAnonymousBoardComment(cmdAnonymousBoardCommentDTO));
    }

    @DisplayName("익명게시판 게시글 댓글 삭제 성공 여부 테스트")
    @Test
    void deleteAnonymousBoardComment_ValidId_Success() {
        // given
        CmdAnonymousBoardCommentDTO savedCmdAnonymousBoardCommentDTO = cmdAnonymousBoardCommentService.saveAnonymousBoardComment(cmdAnonymousBoardCommentDTO);

        // when
        cmdAnonymousBoardCommentService.deleteAnonymousBoardComment(savedCmdAnonymousBoardCommentDTO.getId());

        // then
        assertFalse(cmdAnonymousBoardCommentRepository.findById(savedCmdAnonymousBoardCommentDTO.getId()).isPresent());
    }

    @DisplayName("익명게시판 게시글 댓글 유효하지 않은 ID로 삭제 시도 시 예외 발생 여부 테스트")
    @Test
    void deleteAnonymousBoardComment_InvalidId_ThrowsNoSuchElementException() {
        // given
        int invalidId = 9999;

        // when & then
        assertThrows(NoSuchElementException.class, () -> cmdAnonymousBoardCommentService.deleteAnonymousBoardComment(invalidId));
    }

    @DisplayName("맥 어드레스 유효성 검증 테스트")
    @Test
    void saveAnonymousBoardComment_verifyMacAddress() throws Exception {
        // when
        CmdAnonymousBoardCommentDTO savedDto = cmdAnonymousBoardCommentService.saveAnonymousBoardComment(cmdAnonymousBoardCommentDTO);

        // then
        assertThat(savedDto.getContent()).isEqualTo(cmdAnonymousBoardCommentDTO.getContent());
        assertThat(savedDto.getAnonymousBoard().getId()).isEqualTo(savedAnonymousBoard.getId());

        // MAC 주소 검증
        String actualMacAddress = savedDto.getMacAddress();
        assertThat(actualMacAddress).isNotNull();
        assertThat(isValidMacAddress(actualMacAddress)).isTrue();
    }

    @DisplayName("실제 맥 주소와 일치하는지 테스트")
    @Test
    void saveAnonymousBoardComment_checkMacAddress() throws Exception {
        // when
        CmdAnonymousBoardCommentDTO savedDto = cmdAnonymousBoardCommentService.saveAnonymousBoardComment(cmdAnonymousBoardCommentDTO);

        // then
        assertThat(savedDto.getContent()).isEqualTo(cmdAnonymousBoardCommentDTO.getContent());
        assertThat(savedDto.getAnonymousBoard().getId()).isEqualTo(savedAnonymousBoard.getId());

        // MAC 주소 검증
        String actualMacAddress = savedDto.getMacAddress();
        assertThat(actualMacAddress).isNotNull();
        assertThat(actualMacAddress).isEqualTo(MacAddressUtil.getMacAddress());
    }

    // MAC 주소 유효성 검사 메서드
    private boolean isValidMacAddress(String macAddress) {
        String macRegex = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
        return macAddress.matches(macRegex);
    }
}