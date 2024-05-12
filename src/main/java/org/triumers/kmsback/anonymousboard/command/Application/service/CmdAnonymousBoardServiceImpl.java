package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    // 게시글 작성
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

    // 게시글 삭제
    @Transactional
    public void deleteAnonymousBoard(int id) {
        cmdAnonymousBoardRepository.deleteById(id);
    }

}
