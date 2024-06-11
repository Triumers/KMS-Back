package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoard;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoardComment;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardCommentRepository;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardRepository;
import org.triumers.kmsback.common.util.MacAddressUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CmdAnonymousBoardServiceImpl implements CmdAnonymousBoardService {

    private final CmdAnonymousBoardRepository cmdAnonymousBoardRepository;

    private final CmdAnonymousBoardCommentRepository cmdAnonymousBoardCommentRepository;

    @Autowired
    public CmdAnonymousBoardServiceImpl(CmdAnonymousBoardRepository cmdAnonymousBoardRepository, CmdAnonymousBoardCommentRepository cmdAnonymousBoardCommentRepository) {
        this.cmdAnonymousBoardRepository = cmdAnonymousBoardRepository;
        this.cmdAnonymousBoardCommentRepository = cmdAnonymousBoardCommentRepository;
    }

    // 엔티티를 DTO로 변환하는 메서드
    private CmdAnonymousBoardDTO convertToDto(CmdAnonymousBoard cmdAnonymousBoard) {
        return new CmdAnonymousBoardDTO(
                cmdAnonymousBoard.getId(),
                cmdAnonymousBoard.getNickname(),
                cmdAnonymousBoard.getTitle(),
                cmdAnonymousBoard.getContent(),
                cmdAnonymousBoard.getCreatedDate(),
                cmdAnonymousBoard.getMacAddress()
        );
    }

    // 게시글 작성
    @Transactional
    public CmdAnonymousBoardDTO saveAnonymousBoard(CmdAnonymousBoardDTO cmdAnonymousBoardDTO) {
        // 입력 값 유효성 검사
        if (cmdAnonymousBoardDTO.getTitle() == null || cmdAnonymousBoardDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title is required.");
        }
        if (cmdAnonymousBoardDTO.getContent() == null || cmdAnonymousBoardDTO.getContent().isEmpty()) {
            throw new IllegalArgumentException("Content is required.");
        }

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
        } catch (DataAccessException e) {
            // 데이터베이스 오류 처리
            throw new RuntimeException("Failed to save anonymous board.", e);
        } catch (Exception e) {
            // MAC 주소를 가져오는 과정에서 예외 발생 시 처리
            throw new RuntimeException("Failed to get MAC address.", e);
        }
    }

    // 게시글 삭제
    @Transactional
    public void deleteAnonymousBoard(int id) {
        Optional<CmdAnonymousBoard> optionalCmdAnonymousBoard = cmdAnonymousBoardRepository.findById(id);
        CmdAnonymousBoard cmdAnonymousBoard = optionalCmdAnonymousBoard.orElseThrow(
                () -> new NoSuchElementException("Anonymous board not found with id: " + id)
        );

        // 게시글의 모든 댓글 삭제
        List<CmdAnonymousBoardComment> comments = cmdAnonymousBoard.getComments();
        for (CmdAnonymousBoardComment comment : comments) {
            cmdAnonymousBoardCommentRepository.delete(comment);
        }

        // 게시글 삭제
        cmdAnonymousBoardRepository.delete(cmdAnonymousBoard);
    }
}