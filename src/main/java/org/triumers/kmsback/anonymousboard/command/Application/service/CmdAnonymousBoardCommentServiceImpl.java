package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoardComment;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardCommentRepository;
import org.triumers.kmsback.common.util.MacAddressUtil;

@Service
public class CmdAnonymousBoardCommentServiceImpl implements CmdAnonymousBoardCommentService{

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

    // 댓글 조회(페이징 처리까지)
    @Transactional(readOnly = true)
    public Page<CmdAnonymousBoardCommentDTO> findAllAnonymousBoardComment(int anonymousBoardId, Pageable pageable) {
        Page<CmdAnonymousBoardComment> cmdAnonymousBoardCommentPage = cmdAnonymousBoardCommentRepository.findByAnonymousBoardId(anonymousBoardId, pageable);
        return cmdAnonymousBoardCommentPage.map(this::convertToDto);
    }

    // 댓글 작성
    @Transactional
    public CmdAnonymousBoardCommentDTO saveAnonymousBoardComment(CmdAnonymousBoardCommentDTO cmdAnonymousBoardCommentDTO) {
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
        } catch (Exception e) {
            // MAC 주소를 가져오는 과정에서 예외 발생 시 처리
            // 예외 처리 로직 추가
            e.printStackTrace();
            throw new RuntimeException("Failed to get MAC address.", e);
        }
    }

    // 댓글 삭제
    @Transactional
    public void deleteAnonymousBoardComment(int id) {
        cmdAnonymousBoardCommentRepository.deleteById(id);
    }
}
