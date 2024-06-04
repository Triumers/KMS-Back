package org.triumers.kmsback.organization.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.organization.command.Application.dto.CmdCenterDTO;
import org.triumers.kmsback.organization.command.Application.dto.CmdDepartmentDTO;
import org.triumers.kmsback.organization.command.Application.service.CmdCenterService;
import org.triumers.kmsback.organization.command.Application.service.CmdDepartmentService;
import org.triumers.kmsback.organization.query.dto.QryDepartmentDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QryDepartmentServiceTest {

    private final String TEST_DEPARTMENT_NAME = "UniqDepartmentName";

    @Autowired
    private CmdCenterService cmdCenterService;

    @Autowired
    private CmdDepartmentService cmdDepartmentService;

    @Autowired
    private QryDepartmentService qryDepartmentService;

    @Autowired
    private QryCenterService qryCenterService;

    @DisplayName("부서 검색 by Id")
    @Test
    void findDepartmentByIdTest() {

        // given
        int departmentId = addDepartmentForTest();

        // when
        QryDepartmentDTO departmentDTO = qryDepartmentService.findDepartmentById(departmentId);

        // then
        assertNotNull(departmentDTO);
        assertEquals(departmentDTO.getName(), TEST_DEPARTMENT_NAME);
        assertEquals(departmentDTO.getCenterName(), qryCenterService.findCenterById(departmentDTO.getCenterId()).getName());
    }

    @DisplayName("부서 검색 by 부서명")
    @Test
    void findDepartmentListByNameTest() {

        // given
        int centerId = addCenterForTest();

        List<String> names = new ArrayList<>();
        String target = "UniqWord";

        names.add(target);
        names.add(target + "테스트");
        names.add("테스트" + target);
        names.add("테스트" + target + "테스트");

        for (String name : names) {
            addDepartmentForTest(centerId, name);
        }

        // when
        List<QryDepartmentDTO> resultList = qryDepartmentService.findDepartmentListByName(target);

        // then
        for (QryDepartmentDTO result : resultList) {     // 결과가 검색어를 포함하는지 여부
            assertTrue(result.getName().contains(target));
        }

        assertTrue(resultList.size() >= names.size());  // 테스트용으로 추가한 값 이상으로 검색되는지
    }

    @DisplayName("부서 검색 by 본부 Id")
    @Test
    void findDepartmentListByCenterIdTest() {

        // given
        int centerId = addCenterForTest();

        List<String> names = new ArrayList<>();

        names.add("테스트 부서 1");
        names.add("테스트 부서 2");
        names.add("테스트 부서 3");
        names.add("테스트 부서 4");
        names.add("테스트 부서 5");

        for (String name : names) {
            addDepartmentForTest(centerId, name);
        }

        // when
        List<QryDepartmentDTO> resultList = qryDepartmentService.findDepartmentListByCenterId(centerId);
        List<String> resultNames = new ArrayList<>();
        for (QryDepartmentDTO result : resultList) {
            resultNames.add(result.getName());
        }

        // then
        for (String name : names) {
            assertTrue(resultNames.contains(name));
        }
    }

    private int addCenterForTest() {
        CmdCenterDTO cmdCenterDTO = new CmdCenterDTO();
        cmdCenterDTO.setName("UniqCenterName");
        return cmdCenterService.addCenter(cmdCenterDTO);
    }

    private int addDepartmentForTest() {
        int centerId = addCenterForTest();
        CmdDepartmentDTO departmentDTO = new CmdDepartmentDTO();
        departmentDTO.setName(TEST_DEPARTMENT_NAME);
        departmentDTO.setCenterId(centerId);
        return cmdDepartmentService.addDepartment(departmentDTO);
    }

    private void addDepartmentForTest(int centerId, String departmentName) {
        CmdDepartmentDTO departmentDTO = new CmdDepartmentDTO();
        departmentDTO.setName(departmentName);
        departmentDTO.setCenterId(centerId);
        cmdDepartmentService.addDepartment(departmentDTO);
    }
}