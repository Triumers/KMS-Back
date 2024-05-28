package org.triumers.kmsback.approval.command.Application.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.kmsback.approval.command.Application.dto.CmdApprovalRequestDTO;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdApproval;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdApprovalType;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdRequestApproval;
import org.triumers.kmsback.approval.command.domain.repository.CmdApprovalRepository;
import org.triumers.kmsback.approval.command.domain.repository.CmdApprovalTypeRepository;
import org.triumers.kmsback.approval.command.domain.repository.CmdRequestApprovalRepository;
import org.triumers.kmsback.common.LoggedInUser;
import org.triumers.kmsback.common.TestUserInfo;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.user.command.Application.dto.ManageUserDTO;
import org.triumers.kmsback.user.command.Application.service.AuthService;
import org.triumers.kmsback.user.command.Application.service.ManagerService;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;
import org.triumers.kmsback.user.command.domain.repository.EmployeeRepository;

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

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CmdApprovalTypeRepository approvalTypeRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private LoggedInUser loggedInUser;

    @Autowired
    private ManagerService managerService;

    @BeforeEach
    public void setup() {
        // 기존 데이터 삭제
        requestApprovalRepository.deleteAll();
        approvalRepository.deleteAll();
    }

    @Transactional
    @Test
    void createApproval() throws NotLoginException {
        // given
        loggedInUser.setting();
        addHrManagerForTest();

        int requesterId = authService.whoAmI().getId();
        int approverId = employeeRepository.findByEmail(TestUserInfo.HR_MANAGER_EMAIL).getId();

        int typeId = 1;
        String content = "Test Approval";

        CmdApprovalRequestDTO requestDto = new CmdApprovalRequestDTO();
        requestDto.setApproverId(approverId);
        requestDto.setTypeId(typeId);
        requestDto.setContent(content);

        // when
        cmdRequestApprovalService.createApproval(requestDto);

        // then
        List<CmdApproval> approvals = approvalRepository.findAll();
        assertThat(approvals).hasSize(1);

        CmdApproval createdApproval = approvals.get(0);
        assertThat(createdApproval.getContent()).isEqualTo(content);
        assertThat(createdApproval.getType().getId()).isEqualTo(typeId);
        assertThat(createdApproval.getRequester().getId()).isEqualTo(requesterId);

        List<CmdRequestApproval> requestApprovals = requestApprovalRepository.findAll();
        assertThat(requestApprovals).hasSize(1);

        CmdRequestApproval createdRequestApproval = requestApprovals.get(0);
        assertThat(createdRequestApproval.getApprovalOrder()).isEqualTo(1);
        assertThat(createdRequestApproval.getApprover().getId()).isEqualTo(approverId);
        assertThat(createdRequestApproval.getApproval().getId()).isEqualTo(createdApproval.getId());
    }

    @Transactional
    @Test
    void cancelApproval() throws NotLoginException {
        // given
        loggedInUser.setting();
        addHrManagerForTest();

        int requesterId = authService.whoAmI().getId();
        int approverId = employeeRepository.findByEmail(TestUserInfo.HR_MANAGER_EMAIL).getId();

        CmdApprovalRequestDTO requestDto = new CmdApprovalRequestDTO();
        requestDto.setContent("Test Approval");
        requestDto.setTypeId(1);
        requestDto.setApproverId(approverId);

        cmdRequestApprovalService.createApproval(requestDto);

        CmdApproval approval = approvalRepository.findByRequesterIdAndIdIsNotNull(requesterId)
                .orElseThrow(() -> new IllegalArgumentException("Approval not found for requester id: " + requesterId));

        // when
        cmdRequestApprovalService.cancelApproval(approval.getId());

        // then
        List<CmdRequestApproval> requestApprovals = requestApprovalRepository.findByApprovalIdOrderByApprovalOrderAsc(approval.getId());
        assertTrue(requestApprovals.stream().allMatch(ra -> ra.isCanceled()));
    }

    @Transactional
    @Test
    void approveRequestApproval() throws NotLoginException {
        // Given
        loggedInUser.settingHrManager();
        addUserForTest();

        int approverId = authService.whoAmI().getId();
        int requesterId = employeeRepository.findByEmail(TestUserInfo.EMAIL).getId();

        Employee approver = employeeRepository.findById(approverId);
        Employee requester = employeeRepository.findById(requesterId);
        CmdApprovalType approvalType = approvalTypeRepository.findById(1).orElseThrow();

        CmdApproval approval = new CmdApproval();
        approval.setRequester(requester);
        approval.setType(approvalType);
        approval = approvalRepository.save(approval);
        System.out.println("approval = " + approval);

        CmdRequestApproval requestApproval = new CmdRequestApproval();
        requestApproval.setApproval(approval);
        requestApproval.setApprover(approver);
        requestApproval.setIsApproved("WAITING");
        requestApprovalRepository.save(requestApproval);
        System.out.println("requestApproval = " + requestApproval);

        // When
        cmdRequestApprovalService.approveRequestApproval(requestApproval.getId());
        System.out.println("requestApproval = " + requestApproval);

        // Then
        CmdRequestApproval updatedRequestApproval = requestApprovalRepository.findById(requestApproval.getId()).orElseThrow();
        assertEquals("APPROVED", updatedRequestApproval.getIsApproved());
    }

    @Transactional
    @Test
    void approveRequestApprovalAlreadyProcessed() throws NotLoginException {
        // Given
        loggedInUser.settingHrManager();
        addUserForTest();

        int approverId = authService.whoAmI().getId();
        int requesterId = employeeRepository.findByEmail(TestUserInfo.EMAIL).getId();

        Employee approver = employeeRepository.findById(approverId);
        Employee requester = employeeRepository.findById(requesterId);
        CmdApprovalType approvalType = approvalTypeRepository.findById(2).orElseThrow();

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
        assertThrows(IllegalStateException.class, () -> cmdRequestApprovalService.approveRequestApproval(requestApproval.getId()));
    }

    @Transactional
    @Test
    void rejectRequestApproval() throws NotLoginException {
        // Given
        loggedInUser.settingHrManager();
        addUserForTest();

        int approverId = authService.whoAmI().getId();
        int requesterId = employeeRepository.findByEmail(TestUserInfo.EMAIL).getId();

        Employee approver = employeeRepository.findById(approverId);
        Employee requester = employeeRepository.findById(requesterId);
        CmdApprovalType approvalType = approvalTypeRepository.findById(2).orElseThrow();

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
        cmdRequestApprovalService.rejectRequestApproval(requestApproval.getId());

        // Then
        CmdRequestApproval updatedRequestApproval = requestApprovalRepository.findById(requestApproval.getId()).orElseThrow();
        assertEquals("REJECTED", updatedRequestApproval.getIsApproved());
    }

    @Transactional
    @Test
    void rejectRequestApprovalAlreadyProcessed() throws NotLoginException {
        // Given
        loggedInUser.settingHrManager();
        addUserForTest();

        int approverId = authService.whoAmI().getId();
        int requesterId = employeeRepository.findByEmail(TestUserInfo.EMAIL).getId();

        Employee approver = employeeRepository.findById(approverId);
        Employee requester = employeeRepository.findById(requesterId);
        CmdApprovalType approvalType = approvalTypeRepository.findById(2).orElseThrow();

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
        assertThrows(IllegalStateException.class, () -> cmdRequestApprovalService.rejectRequestApproval(requestApproval.getId()));
    }

    @Transactional
    @Test
    void requestApprovalUnauthorized() throws NotLoginException {
        // Given
        loggedInUser.settingHrManager();
        addUserForTest();

        int approverId = employeeRepository.findByEmail(TestUserInfo.EMAIL).getId();
        int requesterId = employeeRepository.findByEmail(TestUserInfo.EMAIL).getId();

        Employee approver = employeeRepository.findById(approverId);
        Employee requester = employeeRepository.findById(requesterId);
        CmdApprovalType approvalType = approvalTypeRepository.findById(2).orElseThrow();

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
        assertThrows(IllegalArgumentException.class, () -> cmdRequestApprovalService.approveRequestApproval(requestApproval.getId()));
        assertThrows(IllegalArgumentException.class, () -> cmdRequestApprovalService.rejectRequestApproval(requestApproval.getId()));
    }

    @Transactional
    @Test
    void addApproverToRequestApproval() throws NotLoginException {
        // Given
        loggedInUser.settingHrManager();
        addUserForTest();

        int approverId = authService.whoAmI().getId();
        int requesterId = employeeRepository.findByEmail(TestUserInfo.EMAIL).getId();
        int otherEmployeeId = employeeRepository.findByEmail(TestUserInfo.HR_MANAGER_EMAIL).getId();

        Employee requester = employeeRepository.findById(requesterId);
        Employee approver = employeeRepository.findById(approverId);
        Employee newApprover = employeeRepository.findById(otherEmployeeId);
        CmdApprovalType approvalType = approvalTypeRepository.findById(1).orElseThrow();

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
        cmdRequestApprovalService.addApproverToRequestApproval(requestApproval.getId(), newApprover.getId());

        // Then
        List<CmdRequestApproval> requestApprovals = requestApprovalRepository.findByApprovalIdOrderByApprovalOrderAsc(approval.getId());
        assertEquals(2, requestApprovals.size());
        assertEquals("APPROVED", requestApprovals.get(0).getIsApproved());
        assertEquals(newApprover.getId(), requestApprovals.get(1).getApprover().getId());
        assertEquals("WAITING", requestApprovals.get(1).getIsApproved());
    }

    private void addUserForTest() {
        ManageUserDTO userDTO = new ManageUserDTO(TestUserInfo.EMAIL, TestUserInfo.PASSWORD, TestUserInfo.NAME, null,
                TestUserInfo.USER_ROLE, null, null, TestUserInfo.PHONE_NUMBER, 1, 1,
                1);
        managerService.signup(userDTO);
    }

    private void addHrManagerForTest() {
        ManageUserDTO userDTO = new ManageUserDTO(TestUserInfo.HR_MANAGER_EMAIL, TestUserInfo.PASSWORD, TestUserInfo.NAME, null,
                TestUserInfo.HR_MANAGER_ROLE, null, null, TestUserInfo.PHONE_NUMBER, 1, 1,
                1);
        managerService.signup(userDTO);
    }
}