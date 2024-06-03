package org.triumers.kmsback.approval.query.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.approval.command.Application.dto.CmdApprovalRequestDTO;
import org.triumers.kmsback.approval.command.Application.service.CmdRequestApprovalService;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdApproval;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdRequestApproval;
import org.triumers.kmsback.approval.command.domain.repository.CmdApprovalRepository;
import org.triumers.kmsback.approval.command.domain.repository.CmdRequestApprovalRepository;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalInfoDTO;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalWithEmployeeDTO;
import org.triumers.kmsback.common.LoggedInUser;
import org.triumers.kmsback.common.TestUserInfo;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.user.command.Application.dto.ManageUserDTO;
import org.triumers.kmsback.user.command.Application.service.AuthService;
import org.triumers.kmsback.user.command.Application.service.ManagerService;
import org.triumers.kmsback.user.command.domain.repository.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class QryRequestApprovalServiceImplTests {

    @Autowired
    private QryRequestApprovalService qryRequestApprovalService;

    @Autowired
    private CmdApprovalRepository approvalRepository;

    @Autowired
    private CmdRequestApprovalRepository requestApprovalRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CmdRequestApprovalService cmdRequestApprovalService;

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

    @Test
    public void findByIdTest() throws NotLoginException, WrongInputValueException {
        // given
        requesterSetUp();
        int requesterId = authService.whoAmI().getId();
        int approverId = employeeRepository.findByEmail(TestUserInfo.HR_MANAGER_EMAIL).getId();

        // when
        List<CmdApproval> approvals = approvalRepository.findAll();
        int approvalId = approvals.get(0).getId();
        QryRequestApprovalWithEmployeeDTO result = qryRequestApprovalService.findById(approvalId);

        // then
        assertEquals(approvalId, result.getApprovalInfo().getApprovalId());
        assertEquals(requesterId, result.getRequester().getId());
        assertEquals(approverId, result.getApprovalInfo().getApproverId());
        assertEquals(1, result.getApprovalInfo().getApprovalOrder());
        assertEquals("Test Content", result.getApprovalInfo().getContent());
        assertEquals("워크스페이스 생성 요청", result.getApprovalInfo().getType());
    }

    @Test
    public void findAllTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        requesterSetUp();
        int requesterId = authService.whoAmI().getId();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findAll(page, size);

        // then
        int expectedTotalCount = 4; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getRequesterId() == requesterId));
    }

    @Test
    public void findByTypeTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        int typeId = 1;
        requesterSetUp();
        int requesterId = authService.whoAmI().getId();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findByType(typeId, page, size);

        // then
        int expectedTotalCount = 3; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getRequesterId() == requesterId));
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getTypeId() == typeId));
        String expectedType = "워크스페이스 생성 요청"; // typeId에 해당하는 실제 type 값을 가져와서 사용
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getType().equals(expectedType)));
    }

    @Test
    public void findByDateRangeTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        LocalDateTime startDate = LocalDateTime.of(2024, 4, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 6, 30, 23, 59);
        requesterSetUp();
        int requesterId = authService.whoAmI().getId();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findByDateRange(startDate, endDate, page, size);

        // then
        int expectedTotalCount = 4; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getRequesterId() == requesterId));
        assertTrue(approvalInfoDTOS.stream().allMatch(dto ->
                dto.getCreatedAt().isAfter(startDate) && dto.getCreatedAt().isBefore(endDate)
        ));
    }

    @Test
    public void findReceivedByIdTest() throws NotLoginException, WrongInputValueException {
        // given
        approverSetUp();
        int approverId = authService.whoAmI().getId();

        // when
        List<CmdRequestApproval> requestApprovals = requestApprovalRepository.findAll();
        int requestApprovalId = requestApprovals.get(0).getId();
        QryRequestApprovalWithEmployeeDTO result = qryRequestApprovalService.findReceivedById(requestApprovalId);

        // then
        assertEquals(requestApprovalId, result.getApprovalInfo().getRequestApprovalId());
        assertEquals(approverId, result.getApprover().getId());
        assertEquals(approverId, result.getApprovalInfo().getApproverId());
        assertEquals("Test Content", result.getApprovalInfo().getContent());
        assertEquals("스터디 생성 요청", result.getApprovalInfo().getType());
    }

    @Test
    public void findAllReceivedTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        approverSetUp();
        int approverId = authService.whoAmI().getId();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findAllReceived(page, size);

        // then
        int expectedTotalCount = 4; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getApproverId() == approverId));
    }

    @Test
    public void findReceivedByTypeTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        int typeId = 2;
        approverSetUp();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findReceivedByType(typeId, page, size);

        // then
        int expectedTotalCount = 3; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getTypeId() == typeId));
        String expectedType = "스터디 생성 요청"; // typeId에 해당하는 실제 type 값을 가져와서 사용
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getType().equals(expectedType)));
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getContent().equals("Test Content")));
    }

    @Test
    public void findReceivedByDateRangeTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        LocalDateTime startDate = LocalDateTime.of(2024, 4, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 6, 30, 23, 59);
        approverSetUp();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findReceivedByDateRange(startDate, endDate, page, size);

        // then
        int expectedTotalCount = 4; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto ->
                dto.getCreatedAt().isAfter(startDate) && dto.getCreatedAt().isBefore(endDate)
        ));
    }

    @Test
    public void findByContentTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        String keyword = "Content";
        requesterSetUp();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findByContent(keyword, page, size);

        // then
        int expectedTotalCount = 3; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getContent().contains(keyword)));
    }

    @Test
    public void findByStatusTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        String status = "WAITING";
        requesterSetUp();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findByStatus(status, page, size);

        // then
        int expectedTotalCount = 4; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getIsApproved().equals(status)));
    }

    @Test
    public void findReceivedByContentTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        String keyword = "Content";
        approverSetUp();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findReceivedByContent(keyword, page, size);

        // then
        int expectedTotalCount = 3; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getContent().contains(keyword)));
    }

    @Test
    public void findReceivedByStatusTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        String status = "WAITING";
        approverSetUp();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findReceivedByStatus(status, page, size);

        // then
        int expectedTotalCount = 4; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getIsApproved().equals(status)));
    }


    private void addHrManagerForTest() {
        ManageUserDTO userDTO = new ManageUserDTO(TestUserInfo.HR_MANAGER_EMAIL, TestUserInfo.PASSWORD, TestUserInfo.NAME, null,
                TestUserInfo.HR_MANAGER_ROLE, null, null, TestUserInfo.PHONE_NUMBER, 1, 1,
                1);
        managerService.signup(userDTO);
    }

    private void requesterSetUp() throws NotLoginException {
        loggedInUser.setting();
        addHrManagerForTest();

        int requesterId = authService.whoAmI().getId();
        int approverId = employeeRepository.findByEmail(TestUserInfo.HR_MANAGER_EMAIL).getId();

        int typeId = 1;
        String content = "Test Content";

        CmdApprovalRequestDTO requestDto = new CmdApprovalRequestDTO();
        requestDto.setApproverId(approverId);
        requestDto.setTypeId(typeId);
        requestDto.setContent(content);

        int secondTypeId = 3;
        String secondContent = "Test Approval";
        CmdApprovalRequestDTO secondRequestDto = new CmdApprovalRequestDTO();
        secondRequestDto.setApproverId(approverId);
        secondRequestDto.setTypeId(secondTypeId);
        secondRequestDto.setContent(secondContent);

        // when
        cmdRequestApprovalService.createApproval(requestDto);
        cmdRequestApprovalService.createApproval(requestDto);
        cmdRequestApprovalService.createApproval(requestDto);
        cmdRequestApprovalService.createApproval(secondRequestDto);

    }

    private void approverSetUp() throws NotLoginException {
        loggedInUser.setting();
        addHrManagerForTest();

        int approverId = authService.whoAmI().getId();

        int typeId = 2;
        String content = "Test Content";

        CmdApprovalRequestDTO requestDto = new CmdApprovalRequestDTO();
        requestDto.setApproverId(approverId);
        requestDto.setTypeId(typeId);
        requestDto.setContent(content);

        int secondTypeId = 3;
        String secondContent = "Test Approval";
        CmdApprovalRequestDTO secondRequestDto = new CmdApprovalRequestDTO();
        secondRequestDto.setApproverId(approverId);
        secondRequestDto.setTypeId(secondTypeId);
        secondRequestDto.setContent(secondContent);

        // when
        cmdRequestApprovalService.createApproval(requestDto);
        cmdRequestApprovalService.createApproval(requestDto);
        cmdRequestApprovalService.createApproval(requestDto);
        cmdRequestApprovalService.createApproval(secondRequestDto);
    }
}