package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardRepository;
import org.triumers.kmsback.common.util.MacAddressUtil;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CmdAnonymousBoardServiceImplTests {

    @Autowired
    private CmdAnonymousBoardRepository cmdAnonymousBoardRepository;

    @Autowired
    private CmdAnonymousBoardService cmdAnonymousBoardService;

    private CmdAnonymousBoardDTO cmdAnonymousBoardDTO;

    @BeforeEach
    void setUp() {
        cmdAnonymousBoardDTO = new CmdAnonymousBoardDTO();
        cmdAnonymousBoardDTO.setTitle("Test Title");
        cmdAnonymousBoardDTO.setContent("Test Content");
    }

    // 유효한 입력값으로 게시글 저장 성공 여부를 확인
    @Test
    void saveAnonymousBoard_ValidInput_Success() {
        // when
        CmdAnonymousBoardDTO savedCmdAnonymousBoardDTO = cmdAnonymousBoardService.saveAnonymousBoard(cmdAnonymousBoardDTO);

        // then
        assertNotNull(savedCmdAnonymousBoardDTO);
        assertEquals(cmdAnonymousBoardDTO.getTitle(), savedCmdAnonymousBoardDTO.getTitle());
        assertEquals(cmdAnonymousBoardDTO.getContent(), savedCmdAnonymousBoardDTO.getContent());
        assertNotNull(savedCmdAnonymousBoardDTO.getMacAddress());
    }

    // 제목이 비어있는 경우 예외 발생 여부를 확인
    @Test
    void saveAnonymousBoard_EmptyTitle_ThrowsIllegalArgumentException() {
        // given
        cmdAnonymousBoardDTO.setTitle("");

        // when & then
        assertThrows(IllegalArgumentException.class, () -> cmdAnonymousBoardService.saveAnonymousBoard(cmdAnonymousBoardDTO));
    }

    // 내용이 비어있는 경우 예외 발생 여부를 확인
    @Test
    void saveAnonymousBoard_EmptyContent_ThrowsIllegalArgumentException() {
        // given
        cmdAnonymousBoardDTO.setContent("");

        // when & then
        assertThrows(IllegalArgumentException.class, () -> cmdAnonymousBoardService.saveAnonymousBoard(cmdAnonymousBoardDTO));
    }

    // 유효한 ID로 게시글 삭제 성공 여부를 확인
    @Test
    void deleteAnonymousBoard_ValidId_Success() {
        // given
        CmdAnonymousBoardDTO savedCmdAnonymousBoardDTO = cmdAnonymousBoardService.saveAnonymousBoard(cmdAnonymousBoardDTO);

        // when
        cmdAnonymousBoardService.deleteAnonymousBoard(savedCmdAnonymousBoardDTO.getId());

        // then
        assertFalse(cmdAnonymousBoardRepository.findById(savedCmdAnonymousBoardDTO.getId()).isPresent());
    }

    // 유효하지 않은 ID로 삭제 시도 시 예외 발생 여부를 확인
    @Test
    void deleteAnonymousBoard_InvalidId_ThrowsNoSuchElementException() {
        // given
        int invalidId = 9999;

        // when & then
        assertThrows(NoSuchElementException.class, () -> cmdAnonymousBoardService.deleteAnonymousBoard(invalidId));
    }

    // 맥 어드레스 유효성 검증
    @Test
    void saveAnonymousBoard_verifyMacAddress() throws Exception {
        // when
        CmdAnonymousBoardDTO savedDto = cmdAnonymousBoardService.saveAnonymousBoard(cmdAnonymousBoardDTO);

        // then
        assertThat(savedDto.getTitle()).isEqualTo(cmdAnonymousBoardDTO.getTitle());
        assertThat(savedDto.getContent()).isEqualTo(cmdAnonymousBoardDTO.getContent());

        // MAC 주소 검증
        String actualMacAddress = savedDto.getMacAddress();
        assertThat(actualMacAddress).isNotNull();
        assertThat(isValidMacAddress(actualMacAddress)).isTrue();
    }

    // MAC 주소 유효성 검사 메서드
    private boolean isValidMacAddress(String macAddress) {
        String macRegex = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
        return macAddress.matches(macRegex);
    }

    // 실제 맥 주소와 일치하는지 검증
    @Test
    void saveAnonymousBoard_checkMacAddress() throws Exception {
        // given
        CmdAnonymousBoardDTO dto = new CmdAnonymousBoardDTO();
        dto.setTitle("New Title");
        dto.setContent("New Content");

        // when
        CmdAnonymousBoardDTO savedDto = cmdAnonymousBoardService.saveAnonymousBoard(dto);

        // then
        assertThat(savedDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(savedDto.getContent()).isEqualTo(dto.getContent());

        // MAC 주소 검증
        String actualMacAddress = savedDto.getMacAddress();
        assertThat(actualMacAddress).isNotNull();
        assertThat(actualMacAddress).isEqualTo(MacAddressUtil.getMacAddress());
    }
}