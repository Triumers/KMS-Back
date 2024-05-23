package org.triumers.kmsback.approval.command.Application.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.kmsback.approval.command.Application.dto.CmdApprovalRequestDTO;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdApproval;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdRequestApproval;
import org.triumers.kmsback.approval.command.domain.repository.CmdApprovalRepository;
import org.triumers.kmsback.approval.command.domain.repository.CmdRequestApprovalRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class CmdRequestApprovalServiceImplTests {

    @Autowired
    private CmdApprovalRepository approvalRepository;

    @Autowired
    private CmdRequestApprovalRepository requestApprovalRepository;

    @Autowired
    private CmdRequestApprovalService cmdRequestApprovalService;

    @Test
    void createApproval_ValidInput_ApprovalCreated() {
        // given
        int requesterId = 2;
        int approverId = 3;
        int typeId = 1;
        String content = "Test Approval";

        CmdApprovalRequestDTO requestDto = new CmdApprovalRequestDTO();
        requestDto.setApproverId(approverId);
        requestDto.setTypeId(typeId);
        requestDto.setContent(content);

        // when
        cmdRequestApprovalService.createApproval(requestDto, requesterId);

        // then
        List<CmdApproval> approvals = approvalRepository.findAll();
        assertThat(approvals).hasSize(6);

        CmdApproval createdApproval = approvals.get(5);
        assertThat(createdApproval.getContent()).isEqualTo(content);
        assertThat(createdApproval.getType().getId()).isEqualTo(typeId);
        assertThat(createdApproval.getRequester().getId()).isEqualTo(requesterId);

        List<CmdRequestApproval> requestApprovals = requestApprovalRepository.findAll();
        assertThat(requestApprovals).hasSize(7);

        CmdRequestApproval createdRequestApproval = requestApprovals.get(6);
        assertThat(createdRequestApproval.getApprovalOrder()).isEqualTo(1);
        assertThat(createdRequestApproval.getApprover().getId()).isEqualTo(approverId);
        assertThat(createdRequestApproval.getApproval().getId()).isEqualTo(createdApproval.getId());
    }


    @Test
    void testCancelApproval() {
        // given
        int requesterId = 3;
        CmdApprovalRequestDTO requestDto = new CmdApprovalRequestDTO();
        requestDto.setContent("Test Approval");
        requestDto.setTypeId(1);
        requestDto.setApproverId(4);

        cmdRequestApprovalService.createApproval(requestDto, requesterId);

        CmdApproval approval = approvalRepository.findByRequesterIdAndIdIsNotNull(requesterId)
                .orElseThrow(() -> new IllegalArgumentException("Approval not found for requester id: " + requesterId));

        // when
        cmdRequestApprovalService.cancelApproval(requesterId, approval.getId());

        // then
        List<CmdRequestApproval> requestApprovals = requestApprovalRepository.findByApprovalIdOrderByApprovalOrderAsc(approval.getId());
        assertTrue(requestApprovals.stream().allMatch(ra -> ra.isCanceled()));
    }


}