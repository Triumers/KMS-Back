package org.triumers.kmsback.approval.query.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("본인이 요청한 결재 ID로 단일 조회")
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

    @DisplayName("본인이 요청한 결재 전체 조회")
    @Test
    public void findQryRequestApprovalInfoTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10;
        requesterSetUp();
        int requesterId = authService.whoAmI().getId();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findQryRequestApprovalInfo(
                null, null, null, null, null, false, page, size);

        // then
        int expectedTotalCount = 4; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getRequesterId() == requesterId));
    }

    @DisplayName("본인이 요청한 결재 유형별 조회")
    @Test
    public void findQryRequestApprovalInfoByTypeTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10;
        int typeId = 1;
        requesterSetUp();
        int requesterId = authService.whoAmI().getId();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findQryRequestApprovalInfo(
                typeId, null, null, null, null, false, page, size);

        // then
        int expectedTotalCount = 3; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getRequesterId() == requesterId));
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getTypeId() == typeId));
        String expectedType = "워크스페이스 생성 요청"; // typeId에 해당하는 실제 type 값을 가져와서 사용
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getType().equals(expectedType)));
    }

    @DisplayName("본인이 요청한 결재 기간별 조회")
    @Test
    public void findQryRequestApprovalInfoByDateRangeTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10;
        LocalDateTime startDate = LocalDateTime.of(2024, 4, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 6, 30, 23, 59);
        requesterSetUp();
        int requesterId = authService.whoAmI().getId();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findQryRequestApprovalInfo(
                null, startDate, endDate, null, null, false, page, size);

        // then
        int expectedTotalCount = 4; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getRequesterId() == requesterId));
        assertTrue(approvalInfoDTOS.stream().allMatch(dto ->
                dto.getCreatedAt().isAfter(startDate) && dto.getCreatedAt().isBefore(endDate)
        ));
    }

    @DisplayName("본인이 요청한 결재 키워드로 검색")
    @Test
    public void findQryRequestApprovalInfoByKeywordTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10;
        String keyword = "Content";
        requesterSetUp();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findQryRequestApprovalInfo(
                null, null, null, keyword, null, false, page, size);

        // then
        int expectedTotalCount = 3; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getContent().contains(keyword)));
    }

    @DisplayName("본인이 요청한 결재 승인 상태별 조회")
    @Test
    public void findQryRequestApprovalInfoByIsCanceledTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10;
        boolean isCanceled = false;
        requesterSetUp();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findQryRequestApprovalInfo(
                null, null, null, null, null, isCanceled, page, size);

        // then
        int expectedTotalCount = 4; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.isCanceled() == isCanceled));
    }

    @DisplayName("본인이 요청한 결재 취소 상태별 조회")
    @Test
    public void findQryRequestApprovalInfoByStatusTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10;
        String status = "WAITING";
        requesterSetUp();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findQryRequestApprovalInfo(
                null, null, null, null, status, false, page, size);

        // then
        int expectedTotalCount = 4; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getIsApproved().equals(status)));
    }


    @DisplayName("본인이 요청받은 결재 ID로 단일 조회")
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

    @DisplayName("본인이 요청받은 결재 전체 조회")
    @Test
    public void findAllReceivedTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        approverSetUp();
        int approverId = authService.whoAmI().getId();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findReceivedQryRequestApprovalInfo(
                null, null, null, null, null, false, page, size);

        // then
        int expectedTotalCount = 4; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getApproverId() == approverId));
    }

    @DisplayName("본인이 요청받은 결재 유형별 조회")
    @Test
    public void findReceivedByTypeTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        int typeId = 2;
        approverSetUp();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findReceivedQryRequestApprovalInfo(
                typeId, null, null, null, null, false, page, size);

        // then
        int expectedTotalCount = 3; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getTypeId() == typeId));
        String expectedType = "스터디 생성 요청"; // typeId에 해당하는 실제 type 값을 가져와서 사용
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getType().equals(expectedType)));
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getContent().equals("Test Content")));
    }

    @DisplayName("본인이 요청받은 결재 기간별 조회")
    @Test
    public void findReceivedByDateRangeTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        LocalDateTime startDate = LocalDateTime.of(2024, 4, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 6, 30, 23, 59);
        approverSetUp();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findReceivedQryRequestApprovalInfo(
                null, startDate, endDate, null, null, false, page, size);

        // then
        int expectedTotalCount = 4; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto ->
                dto.getCreatedAt().isAfter(startDate) && dto.getCreatedAt().isBefore(endDate)
        ));
    }

    @DisplayName("본인이 요청받은 결재 키워드로 검색")
    @Test
    public void findReceivedByContentTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        String keyword = "Content";
        approverSetUp();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findReceivedQryRequestApprovalInfo(
                null, null, null, keyword, null, false, page, size);

        // then
        int expectedTotalCount = 3; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getContent().contains(keyword)));
    }

    @DisplayName("본인이 요청받은 결재 승인 상태별 조회")
    @Test
    public void findReceivedByStatusTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        String status = "WAITING";
        approverSetUp();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findReceivedQryRequestApprovalInfo(
                null, null, null, null, status, false, page, size);

        // then
        int expectedTotalCount = 4; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getIsApproved().equals(status)));
    }

    @DisplayName("본인이 요청받은 결재 취소 상태별 조회")
    @Test
    public void findReceivedByIsCanceledTest() throws NotLoginException, WrongInputValueException {
        // given
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수
        boolean isCanceled = false;
        approverSetUp();

        // when
        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findReceivedQryRequestApprovalInfo(
                null, null, null, null, null, isCanceled, page, size);

        // then
        int expectedTotalCount = 4; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.isCanceled() == isCanceled));
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