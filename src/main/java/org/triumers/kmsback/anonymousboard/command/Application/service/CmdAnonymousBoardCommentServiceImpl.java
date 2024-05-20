package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoardComment;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardCommentRepository;
import org.triumers.kmsback.common.util.MacAddressUtil;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CmdAnonymousBoardCommentServiceImpl implements CmdAnonymousBoardCommentService {

    private final CmdAnonymousBoardCommentRepository cmdAnonymousBoardCommentRepository;

    @Autowired
    public CmdAnonymousBoardCommentServiceImpl(CmdAnonymousBoardCommentRepository cmdAnonymousBoardCommentRepository) {
        this.cmdAnonymousBoardCommentRepository = cmdAnonymousBoardCommentRepository;
    }

    // 엔티티를 DTO로 변환하는 메서드
    private CmdAnonymousBoardCommentDTO convertToDto(CmdAnonymousBoardComment cmdAnonymousBoardComment) {
        CmdAnonymousBoardCommentDTO dto = new CmdAnonymousBoardCommentDTO();
        dto.setId(cmdAnonymousBoardComment.getId());
        dto.setNickname(cmdAnonymousBoardComment.getNickname());
        dto.setContent(cmdAnonymousBoardComment.getContent());
        dto.setCreatedDate(cmdAnonymousBoardComment.getCreatedDate());
        dto.setMacAddress(cmdAnonymousBoardComment.getMacAddress());
        dto.setAnonymousBoardId(cmdAnonymousBoardComment.getAnonymousBoardId());
        return dto;
    }

    // 댓글 작성
    @Transactional
    public CmdAnonymousBoardCommentDTO saveAnonymousBoardComment(CmdAnonymousBoardCommentDTO cmdAnonymousBoardCommentDTO) {
        // 입력 값 유효성 검사
        if (cmdAnonymousBoardCommentDTO.getContent() == null || cmdAnonymousBoardCommentDTO.getContent().isEmpty()) {
            throw new IllegalArgumentException("Content is required.");
        }

        try {
            // MAC 주소 가져오기
            String macAddress = MacAddressUtil.getMacAddress();

            CmdAnonymousBoardComment cmdAnonymousBoardComment = new CmdAnonymousBoardComment();
            cmdAnonymousBoardComment.setNickname(cmdAnonymousBoardCommentDTO.getNickname());
            cmdAnonymousBoardComment.setContent(cmdAnonymousBoardCommentDTO.getContent());
            cmdAnonymousBoardComment.setMacAddress(macAddress);
            cmdAnonymousBoardComment.setAnonymousBoardId(cmdAnonymousBoardCommentDTO.getAnonymousBoardId());

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