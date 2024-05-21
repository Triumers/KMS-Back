package org.triumers.kmsback.approval.query.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalInfoDTO;
import org.triumers.kmsback.approval.query.dto.QryRequestApprovalWithEmployeeDTO;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class QryRequestApprovalServiceImplTests {

    @Autowired
    private QryRequestApprovalService qryRequestApprovalService;

    @Test
    public void findByIdTest() {
        int requesterId = 4;
        int approvalId = 1;

        QryRequestApprovalWithEmployeeDTO result = qryRequestApprovalService.findById(requesterId, approvalId);

        assertEquals(approvalId, result.getApprovalInfo().getApprovalId());
        assertEquals(requesterId, result.getRequester().getId());

        assertEquals(2, result.getApprovalInfo().getApproverId());
        assertEquals(2, result.getApprovalInfo().getApprovalOrder());
        assertEquals("content 1", result.getApprovalInfo().getContent());
        assertEquals("워크스페이스 생성 요청", result.getApprovalInfo().getType());
        assertEquals(LocalDateTime.of(2024, 5, 6, 9, 3, 17), result.getApprovalInfo().getCreatedAt());

    }

    @Test
    public void findAllTest() {
        int requesterId = 4;
        int page = 1;
        int size = 10; // 한 페이지에 표시할 결과의 최대 개수

        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findAll(requesterId, page, size);

        int expectedTotalCount = 5; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());

        // 추가적인 검증 로직
        assertTrue(approvalInfoDTOS.stream().allMatch(dto -> dto.getRequesterId() == requesterId));

    }

    @Test
    public void findByTypeTest() {
        int requesterId = 4;
        int typeId = 1;
        int page = 1;
        int size = 10;

        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findByType(requesterId, typeId, page, size);

        int expectedTotalCount = 2; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());

        assertEquals(requesterId, approvalInfoDTOS.get(0).getRequesterId());
        assertEquals(typeId, approvalInfoDTOS.get(0).getTypeId());
        assertEquals(typeId, approvalInfoDTOS.get(1).getTypeId());
        String expectedType = "워크스페이스 생성 요청"; // typeId에 해당하는 실제 type 값을 가져와서 사용
        assertEquals(expectedType, approvalInfoDTOS.get(0).getType());
        assertEquals(expectedType, approvalInfoDTOS.get(1).getType());
    }

    @Test
    public void findByDateRangeTest() {
        int requesterId = 4;
        LocalDateTime startDate = LocalDateTime.of(2024, 4, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 6, 30, 23, 59);
        int page = 1;
        int size = 10;

        List<QryRequestApprovalInfoDTO> approvalInfoDTOS = qryRequestApprovalService.findByDateRange(requesterId, startDate, endDate, page, size);

        int expectedTotalCount = 3; // 예상되는 총 결과 개수
        assertEquals(expectedTotalCount, approvalInfoDTOS.size());
        assertEquals(requesterId, approvalInfoDTOS.get(0).getRequesterId());
        assertEquals(requesterId, approvalInfoDTOS.get(1).getRequesterId());
        assertEquals(requesterId, approvalInfoDTOS.get(2).getRequesterId());

        assertTrue(approvalInfoDTOS.stream().allMatch(dto ->
                dto.getCreatedAt().isAfter(startDate) && dto.getCreatedAt().isBefore(endDate)
        ));
    }

}