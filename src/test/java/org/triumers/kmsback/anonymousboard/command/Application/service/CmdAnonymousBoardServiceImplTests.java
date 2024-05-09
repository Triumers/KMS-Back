package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoard;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CmdAnonymousBoardServiceImplTests {

    @Mock
    private CmdAnonymousBoardRepository cmdAnonymousBoardRepository;

    @InjectMocks
    private CmdAnonymousBoardServiceImpl cmdAnonymousBoardService;

    private CmdAnonymousBoard cmdAnonymousBoard1;
    private CmdAnonymousBoard cmdAnonymousBoard2;

    @BeforeEach
    void setUp() {
        cmdAnonymousBoard1 = new CmdAnonymousBoard("Title 1", "Content 1", "MAC 1");
        cmdAnonymousBoard1.setId(1);
        cmdAnonymousBoard1.setCreatedDate(LocalDateTime.now());

        cmdAnonymousBoard2 = new CmdAnonymousBoard("Title 2", "Content 2", "MAC 2");
        cmdAnonymousBoard2.setId(2);
        cmdAnonymousBoard2.setCreatedDate(LocalDateTime.now());
    }

    // 1. findAllAnonymousBoard 메서드가 페이징 처리된 CmdAnonymousBoard 목록을 반환하는지 확인
    @Test
    void findAllAnonymousBoard_shouldReturnPageOfCmdAnonymousBoardDTO() {
        List<CmdAnonymousBoard> cmdAnonymousBoardList = Arrays.asList(cmdAnonymousBoard1, cmdAnonymousBoard2);
        Page<CmdAnonymousBoard> cmdAnonymousBoardPage = new PageImpl<>(cmdAnonymousBoardList);

        when(cmdAnonymousBoardRepository.findAll(any(Pageable.class))).thenReturn(cmdAnonymousBoardPage);

        Page<CmdAnonymousBoardDTO> result = cmdAnonymousBoardService.findAllAnonymousBoard(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(cmdAnonymousBoardRepository, times(1)).findAll(any(Pageable.class));
    }

    // 2-1. searchAnonymousBoardByTitle 메서드가 제목으로 검색한 CmdAnonymousBoard 목록을 페이징 처리하여 반환하는지 확인
    @Test
    void searchAnonymousBoardByTitle_shouldReturnPageOfCmdAnonymousBoardDTO() {
        String title = "Title";
        List<CmdAnonymousBoard> cmdAnonymousBoardList = Arrays.asList(cmdAnonymousBoard1, cmdAnonymousBoard2);
        Page<CmdAnonymousBoard> cmdAnonymousBoardPage = new PageImpl<>(cmdAnonymousBoardList);

        when(cmdAnonymousBoardRepository.findByTitleContaining(eq(title), any(Pageable.class))).thenReturn(cmdAnonymousBoardPage);

        Page<CmdAnonymousBoardDTO> result = cmdAnonymousBoardService.searchAnonymousBoardByTitle(title, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(cmdAnonymousBoardRepository, times(1)).findByTitleContaining(eq(title), any(Pageable.class));
    }

    // 2-2. searchAnonymousBoardByContent 메서드가 내용으로 검색한 CmdAnonymousBoard 목록을 페이징 처리하여 반환하는지 확인
    @Test
    void searchAnonymousBoardByContent_shouldReturnPageOfCmdAnonymousBoardDTO() {
        String content = "Content";
        List<CmdAnonymousBoard> cmdAnonymousBoardList = Arrays.asList(cmdAnonymousBoard1, cmdAnonymousBoard2);
        Page<CmdAnonymousBoard> cmdAnonymousBoardPage = new PageImpl<>(cmdAnonymousBoardList);

        when(cmdAnonymousBoardRepository.findByContentContaining(eq(content), any(Pageable.class))).thenReturn(cmdAnonymousBoardPage);

        Page<CmdAnonymousBoardDTO> result = cmdAnonymousBoardService.searchAnonymousBoardByContent(content, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(cmdAnonymousBoardRepository, times(1)).findByContentContaining(eq(content), any(Pageable.class));
    }

    // 2-3. searchAnonymousBoardByTitleAndContent 메서드가 제목과 내용으로 검색한 CmdAnonymousBoard 목록을 페이징 처리하여 반환하는지 확인
    @Test
    void searchAnonymousBoardByTitleAndContent_shouldReturnPageOfCmdAnonymousBoardDTO() {
        String keyword = "Keyword";
        List<CmdAnonymousBoard> cmdAnonymousBoardList = Arrays.asList(cmdAnonymousBoard1, cmdAnonymousBoard2);
        Page<CmdAnonymousBoard> cmdAnonymousBoardPage = new PageImpl<>(cmdAnonymousBoardList);

        when(cmdAnonymousBoardRepository.findByTitleContainingOrContentContaining(eq(keyword), eq(keyword), any(Pageable.class)))
                .thenReturn(cmdAnonymousBoardPage);

        Page<CmdAnonymousBoardDTO> result = cmdAnonymousBoardService.searchAnonymousBoardByTitleAndContent(keyword, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(cmdAnonymousBoardRepository, times(1))
                .findByTitleContainingOrContentContaining(eq(keyword), eq(keyword), any(Pageable.class));
    }

    // 3. saveAnonymousBoard 메서드가 CmdAnonymousBoard를 저장하고, MAC 주소를 실제로 가져와서 저장하는지 확인
    // MacAddressUtil.getMacAddress() 메서드를 호출하고, 반환된 MAC 주소 값을 검증
    @Test
    void saveAnonymousBoard_shouldSaveCmdAnonymousBoard() throws Exception {
        CmdAnonymousBoardDTO cmdAnonymousBoardDTO = new CmdAnonymousBoardDTO();
        cmdAnonymousBoardDTO.setTitle("Title");
        cmdAnonymousBoardDTO.setContent("Content");

        when(cmdAnonymousBoardRepository.save(any(CmdAnonymousBoard.class))).thenAnswer(invocation -> {
            CmdAnonymousBoard savedCmdAnonymousBoard = invocation.getArgument(0);
            assertNotNull(savedCmdAnonymousBoard.getMacAddress());
            return savedCmdAnonymousBoard;
        });

        CmdAnonymousBoardDTO result = cmdAnonymousBoardService.saveAnonymousBoard(cmdAnonymousBoardDTO);

        assertNotNull(result);
        assertNotNull(result.getMacAddress());
        verify(cmdAnonymousBoardRepository, times(1)).save(any(CmdAnonymousBoard.class));
    }

    // 4. deleteAnonymousBoard 메서드가 주어진 id에 해당하는 CmdAnonymousBoard를 삭제하는지 확인
    @Test
    void deleteAnonymousBoard_shouldDeleteCmdAnonymousBoard() {
        int id = 1;

        cmdAnonymousBoardService.deleteAnonymousBoard(id);

        verify(cmdAnonymousBoardRepository, times(1)).deleteById(eq(id));
    }
}