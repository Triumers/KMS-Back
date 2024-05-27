package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoard;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoardComment;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardCommentRepository;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardRepository;
import org.triumers.kmsback.common.util.MacAddressUtil;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CmdAnonymousBoardCommentServiceImpl implements CmdAnonymousBoardCommentService {

    private final CmdAnonymousBoardCommentRepository cmdAnonymousBoardCommentRepository;
    private final CmdAnonymousBoardRepository cmdAnonymousBoardRepository;

    @Autowired
    public CmdAnonymousBoardCommentServiceImpl(CmdAnonymousBoardCommentRepository cmdAnonymousBoardCommentRepository,
                                               CmdAnonymousBoardRepository cmdAnonymousBoardRepository) {
        this.cmdAnonymousBoardCommentRepository = cmdAnonymousBoardCommentRepository;
        this.cmdAnonymousBoardRepository = cmdAnonymousBoardRepository;
    }

    // 엔티티를 DTO로 변환하는 메서드
    private CmdAnonymousBoardCommentDTO convertToDto(CmdAnonymousBoardComment cmdAnonymousBoardComment) {
        return new CmdAnonymousBoardCommentDTO(
                cmdAnonymousBoardComment.getId(),
                cmdAnonymousBoardComment.getNickname(),
                cmdAnonymousBoardComment.getContent(),
                cmdAnonymousBoardComment.getCreatedDate(),
                cmdAnonymousBoardComment.getMacAddress(),
                cmdAnonymousBoardComment.getAnonymousBoard()
        );
    }

    // 댓글 작성
    @Transactional
    public CmdAnonymousBoardCommentDTO saveAnonymousBoardComment(CmdAnonymousBoardCommentDTO cmdAnonymousBoardCommentDTO) {
        // 입력 값 유효성 검사
        if (cmdAnonymousBoardCommentDTO.getContent() == null || cmdAnonymousBoardCommentDTO.getContent().isEmpty()) {
            throw new IllegalArgumentException("Content is required.");
        }
        if (cmdAnonymousBoardCommentDTO.getAnonymousBoard() == null || cmdAnonymousBoardCommentDTO.getAnonymousBoard().getId() == 0) {
            throw new IllegalArgumentException("Invalid anonymous board.");
        }

        try {
            // MAC 주소 가져오기
            String macAddress = MacAddressUtil.getMacAddress();

            // CmdAnonymousBoard 조회
            CmdAnonymousBoard anonymousBoard = cmdAnonymousBoardRepository.findById(cmdAnonymousBoardCommentDTO.getAnonymousBoard().getId())
                    .orElseThrow(() -> new NoSuchElementException("Anonymous board not found with id: " + cmdAnonymousBoardCommentDTO.getAnonymousBoard().getId()));

            CmdAnonymousBoardComment cmdAnonymousBoardComment = new CmdAnonymousBoardComment(
                    cmdAnonymousBoardCommentDTO.getNickname(),
                    cmdAnonymousBoardCommentDTO.getContent(),
                    macAddress,
                    anonymousBoard
            );

            CmdAnonymousBoardComment savedCmdAnonymousBoardComment = cmdAnonymousBoardCommentRepository.save(cmdAnonymousBoardComment);
            return convertToDto(savedCmdAnonymousBoardComment);
        } catch (DataAccessException e) {
            // 데이터베이스 오류 처리
            throw new RuntimeException("Failed to save anonymous board comment.", e);
        } catch (Exception e) {
            // MAC 주소를 가져오는 과정에서 예외 발생 시 처리
            throw new RuntimeException("Failed to get MAC address.", e);
        }
    }

    // 댓글 삭제
    @Transactional
    public void deleteAnonymousBoardComment(int id) {
        Optional<CmdAnonymousBoardComment> optionalCmdAnonymousBoardComment = cmdAnonymousBoardCommentRepository.findById(id);
        CmdAnonymousBoardComment cmdAnonymousBoardComment = optionalCmdAnonymousBoardComment.orElseThrow(
                () -> new NoSuchElementException("Anonymous board comment not found with id: " + id)
        );
        cmdAnonymousBoardCommentRepository.delete(cmdAnonymousBoardComment);
    }
}