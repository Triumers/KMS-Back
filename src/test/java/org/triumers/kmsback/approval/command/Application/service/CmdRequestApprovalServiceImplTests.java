package org.triumers.kmsback.approval.command.Application.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.kmsback.approval.command.Application.dto.CmdApprovalRequestDTO;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdApproval;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdApprovalType;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdRequestApproval;
import org.triumers.kmsback.approval.command.domain.repository.CmdApprovalRepository;
import org.triumers.kmsback.approval.command.domain.repository.CmdRequestApprovalRepository;
import org.triumers.kmsback.employee.command.domain.aggregate.entity.CmdEmployee;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    void cancelApproval() {
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

    @Test
    void approveRequestApproval() {
        // Given
        CmdEmployee approver = new CmdEmployee();
        approver.setId(1);

        CmdEmployee requester = new CmdEmployee();
        requester.setId(2);

        CmdApprovalType approvalType = new CmdApprovalType();
        approvalType.setId(2); // 결재 유형 ID 2로 설정

        CmdApproval approval = new CmdApproval();
        approval.setRequester(requester);
        approval.setType(approvalType);
        approval = approvalRepository.save(approval);

        CmdRequestApproval requestApproval = new CmdRequestApproval();
        requestApproval.setApproval(approval);
        requestApproval.setApprover(approver);
        requestApproval.setIsApproved("WAITING");
        requestApprovalRepository.save(requestApproval);

        // When
        cmdRequestApprovalService.approveRequestApproval(approver.getId(), requestApproval.getId());

        // Then
        CmdRequestApproval updatedRequestApproval = requestApprovalRepository.findById(requestApproval.getId()).orElseThrow();
        assertEquals("APPROVED", updatedRequestApproval.getIsApproved());
    }

    @Test
    void approveRequestApprovalAlreadyProcessed() {
        // Given
        CmdEmployee approver = new CmdEmployee();
        approver.setId(1);

        CmdEmployee requester = new CmdEmployee();
        requester.setId(2);

        CmdApprovalType approvalType = new CmdApprovalType();
        approvalType.setId(2);

        CmdApproval approval = new CmdApproval();
        approval.setRequester(requester);
        approval.setType(approvalType);
        approval = approvalRepository.save(approval);

        CmdRequestApproval requestApproval = new CmdRequestApproval();
        requestApproval.setApproval(approval);
        requestApproval.setApprover(approver);
        requestApproval.setIsApproved("APPROVED"); // 이미 처리된 상태
        requestApprovalRepository.save(requestApproval);

        // When, Then
        assertThrows(IllegalStateException.class, () -> cmdRequestApprovalService.approveRequestApproval(approver.getId(), requestApproval.getId()));
    }

    @Test
    void rejectRequestApproval() {
        // Given
        CmdEmployee approver = new CmdEmployee();
        approver.setId(1);

        CmdEmployee requester = new CmdEmployee();
        requester.setId(2);

        CmdApprovalType approvalType = new CmdApprovalType();
        approvalType.setId(2);

        CmdApproval approval = new CmdApproval();
        approval.setRequester(requester);
        approval.setType(approvalType);
        approval = approvalRepository.save(approval);

        CmdRequestApproval requestApproval = new CmdRequestApproval();
        requestApproval.setApproval(approval);
        requestApproval.setApprover(approver);
        requestApproval.setIsApproved("WAITING");
        requestApprovalRepository.save(requestApproval);

        // When
        cmdRequestApprovalService.rejectRequestApproval(approver.getId(), requestApproval.getId());

        // Then
        CmdRequestApproval updatedRequestApproval = requestApprovalRepository.findById(requestApproval.getId()).orElseThrow();
        assertEquals("REJECTED", updatedRequestApproval.getIsApproved());
    }

    @Test
    void rejectRequestApprovalAlreadyProcessed() {
        // Given
        CmdEmployee approver = new CmdEmployee();
        approver.setId(1);

        CmdEmployee requester = new CmdEmployee();
        requester.setId(2);

        CmdApprovalType approvalType = new CmdApprovalType();
        approvalType.setId(2);

        CmdApproval approval = new CmdApproval();
        approval.setRequester(requester);
        approval.setType(approvalType);
        approval = approvalRepository.save(approval);

        CmdRequestApproval requestApproval = new CmdRequestApproval();
        requestApproval.setApproval(approval);
        requestApproval.setApprover(approver);
        requestApproval.setIsApproved("APPROVED");
        requestApprovalRepository.save(requestApproval);

        // When, Then
        assertThrows(IllegalStateException.class, () -> cmdRequestApprovalService.rejectRequestApproval(approver.getId(), requestApproval.getId()));
    }

    @Test
    void requestApprovalUnauthorized() {
        // Given
        CmdEmployee approver = new CmdEmployee();
        approver.setId(1);

        CmdEmployee otherEmployee = new CmdEmployee();
        otherEmployee.setId(5);

        CmdEmployee requester = new CmdEmployee();
        requester.setId(3);

        CmdApprovalType approvalType = new CmdApprovalType();
        approvalType.setId(2);

        CmdApproval approval = new CmdApproval();
        approval.setRequester(requester);
        approval.setType(approvalType);
        approval = approvalRepository.save(approval);

        CmdRequestApproval requestApproval = new CmdRequestApproval();
        requestApproval.setApproval(approval);
        requestApproval.setApprover(approver);
        requestApproval.setIsApproved("WAITING");
        requestApprovalRepository.save(requestApproval);

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> cmdRequestApprovalService.approveRequestApproval(otherEmployee.getId(), requestApproval.getId()));
        assertThrows(IllegalArgumentException.class, () -> cmdRequestApprovalService.rejectRequestApproval(otherEmployee.getId(), requestApproval.getId()));
    }

    @Test
    void addApproverToRequestApproval() {
        // Given
        int requesterId = 5;
        int approverId = 2;
        int newApproverId = 3;

        CmdEmployee requester = new CmdEmployee();
        requester.setId(requesterId);

        CmdEmployee approver = new CmdEmployee();
        approver.setId(approverId);

        CmdEmployee newApprover = new CmdEmployee();
        newApprover.setId(newApproverId);

        CmdApprovalType approvalType = new CmdApprovalType();
        approvalType.setId(1);

        CmdApproval approval = new CmdApproval();
        approval.setRequester(requester);
        approval.setType(approvalType);
        approval = approvalRepository.save(approval);

        CmdRequestApproval requestApproval = new CmdRequestApproval();
        requestApproval.setApproval(approval);
        requestApproval.setApprover(approver);
        requestApproval.setIsApproved("WAITING");
        requestApprovalRepository.save(requestApproval);

        // When
        cmdRequestApprovalService.addApproverToRequestApproval(approverId, requestApproval.getId(), newApproverId);

        // Then
        List<CmdRequestApproval> requestApprovals = requestApprovalRepository.findByApprovalIdOrderByApprovalOrderAsc(approval.getId());
        assertEquals(2, requestApprovals.size());
        assertEquals("APPROVED", requestApprovals.get(0).getIsApproved());
        assertEquals(newApproverId, requestApprovals.get(1).getApprover().getId());
        assertEquals("WAITING", requestApprovals.get(1).getIsApproved());
    }

}