package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoard;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardRepository;
import org.triumers.kmsback.common.util.MacAddressUtil;

@Service
public class CmdAnonymousBoardServiceImpl implements CmdAnonymousBoardService {

    private final CmdAnonymousBoardRepository cmdAnonymousBoardRepository;

    @Autowired
    public CmdAnonymousBoardServiceImpl(CmdAnonymousBoardRepository cmdAnonymousBoardRepository) {
        this.cmdAnonymousBoardRepository = cmdAnonymousBoardRepository;
    }

    // 엔티티를 DTO로 변환하는 메서드
    private CmdAnonymousBoardDTO convertToDto(CmdAnonymousBoard cmdAnonymousBoard) {
        CmdAnonymousBoardDTO dto = new CmdAnonymousBoardDTO();
        dto.setId(cmdAnonymousBoard.getId());
        dto.setNickname(cmdAnonymousBoard.getNickname());
        dto.setTitle(cmdAnonymousBoard.getTitle());
        dto.setContent(cmdAnonymousBoard.getContent());
        dto.setCreatedDate(cmdAnonymousBoard.getCreatedDate());
        dto.setMacAddress(cmdAnonymousBoard.getMacAddress());
        return dto;
    }

    // 1. 게시글 조회(페이징 처리까지)
    @Transactional(readOnly = true)
    public Page<CmdAnonymousBoardDTO> findAllAnonymousBoard(Pageable pageable) {
        Page<CmdAnonymousBoard> cmdAnonymousBoardPage = cmdAnonymousBoardRepository.findAll(pageable);
        return cmdAnonymousBoardPage.map(this::convertToDto);
    }

    // 2-1. 제목으로 게시글 검색
    @Transactional(readOnly = true)
    public Page<CmdAnonymousBoardDTO> searchAnonymousBoardByTitle(String title, Pageable pageable) {
        Page<CmdAnonymousBoard> cmdAnonymousBoardPage = cmdAnonymousBoardRepository.findByTitleContaining(title, pageable);
        return cmdAnonymousBoardPage.map(this::convertToDto);
    }

    // 2-2. 내용으로 게시글 검색
    @Transactional(readOnly = true)
    public Page<CmdAnonymousBoardDTO> searchAnonymousBoardByContent(String content, Pageable pageable) {
        Page<CmdAnonymousBoard> cmdAnonymousBoardPage = cmdAnonymousBoardRepository.findByContentContaining(content, pageable);
        return cmdAnonymousBoardPage.map(this::convertToDto);
    }

    // 2-3. 제목+내용으로 게시글 검색
    @Transactional(readOnly = true)
    public Page<CmdAnonymousBoardDTO> searchAnonymousBoardByTitleAndContent(String keyword, Pageable pageable) {
        Page<CmdAnonymousBoard> cmdAnonymousBoardPage = cmdAnonymousBoardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
        return cmdAnonymousBoardPage.map(this::convertToDto);
    }

    // 3. 게시글 작성
    @Transactional
    public CmdAnonymousBoardDTO saveAnonymousBoard(CmdAnonymousBoardDTO cmdAnonymousBoardDTO) {
        try {
            // MAC 주소 가져오기
            String macAddress = MacAddressUtil.getMacAddress();

            CmdAnonymousBoard cmdAnonymousBoard = new CmdAnonymousBoard(
                    cmdAnonymousBoardDTO.getTitle(),
                    cmdAnonymousBoardDTO.getContent(),
                    macAddress
            );

            CmdAnonymousBoard savedCmdAnonymousBoard = cmdAnonymousBoardRepository.save(cmdAnonymousBoard);
            return convertToDto(savedCmdAnonymousBoard);
        } catch (Exception e) {
            // MAC 주소를 가져오는 과정에서 예외 발생 시 처리
            // 예외 처리 로직 추가
            e.printStackTrace();
            throw new RuntimeException("Failed to get MAC address.", e);
        }
    }

    // 4. 게시글 삭제
    @Transactional
    public void deleteAnonymousBoard(int id) {
        cmdAnonymousBoardRepository.deleteById(id);
    }

}
