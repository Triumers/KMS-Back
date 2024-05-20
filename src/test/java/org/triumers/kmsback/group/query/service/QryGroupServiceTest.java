package org.triumers.kmsback.group.query.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.group.query.dto.QryGroupDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
class QryGroupServiceTest {
    @Autowired
    private QryGroupService qryGroupService;
    private QryGroupDTO qryGroupDTO;

    @BeforeEach
    public void setUp() {
        qryGroupDTO = new QryGroupDTO();
        qryGroupDTO.setEmployeeId(1);
        qryGroupDTO.setEmployeeName("관리자");
        qryGroupDTO.setTeamId(1);
        qryGroupDTO.setTeamName("ROOT");
        qryGroupDTO.setDepartmentId(1);
        qryGroupDTO.setDepartmentName("ROOT");
        qryGroupDTO.setCenterId(1);
        qryGroupDTO.setCenterName("ROOT");
        qryGroupDTO.setTabId(1);
        qryGroupDTO.setTopTabId(3);
        qryGroupDTO.setTopTabName("스터디");
        qryGroupDTO.setBottomTabId(1);
        qryGroupDTO.setBottomTabName("자바공부");
        qryGroupDTO.setPublic(false);
        qryGroupDTO.setLeader(true);
    }

    @DisplayName("사원번호로 그룹조회")
    @Test
    void findGroupByEmployeeId() {
        // Given
        List<QryGroupDTO> expectedList = Arrays.asList(qryGroupDTO);

        // When
        List<QryGroupDTO> actualList = qryGroupService.findGroupByEmployeeId(1);

        // Then
        assertEquals(expectedList, actualList);
    }
}