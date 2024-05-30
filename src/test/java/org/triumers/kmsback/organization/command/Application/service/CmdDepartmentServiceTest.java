package org.triumers.kmsback.organization.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.organization.command.Application.dto.CmdCenterDTO;
import org.triumers.kmsback.organization.command.Application.dto.CmdDepartmentDTO;
import org.triumers.kmsback.organization.command.domain.aggregate.entity.CmdDepartment;
import org.triumers.kmsback.organization.command.domain.repository.DepartmentRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CmdDepartmentServiceTest {

    private final String TEST_DEPARTMENT_NAME = "UniqDepartmentNameForTest";

    @Autowired
    private CmdDepartmentService cmdDepartmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CmdCenterService cmdCenterService;

    @DisplayName("부서 생성 테스트")
    @Test
    void addDepartmentTest() {

        // given
        int centerId = addCenterForTest();
        CmdDepartmentDTO departmentDTO = new CmdDepartmentDTO();
        departmentDTO.setName(TEST_DEPARTMENT_NAME);
        departmentDTO.setCenterId(centerId);

        // when
        int departmentId = cmdDepartmentService.addDepartment(departmentDTO);
        CmdDepartment department = departmentRepository.findById(departmentId);

        // then
        assertNotNull(department);
        assertEquals(TEST_DEPARTMENT_NAME, department.getName());
        assertEquals(centerId, department.getCenterId());
    }

    @DisplayName("부서 이름 수정 테스트")
    @Test
    void editDepartmentNameTest() {

        // given
        int departmentId = addDepartmentForTest();
        String newDepartmentName = "새로운 부서 이름";
        CmdDepartmentDTO departmentDTO = new CmdDepartmentDTO();
        departmentDTO.setId(departmentId);
        departmentDTO.setName(newDepartmentName);

        // when
        cmdDepartmentService.editDepartmentName(departmentDTO);
        CmdDepartment department = departmentRepository.findById(departmentId);

        // then
        assertNotEquals(TEST_DEPARTMENT_NAME, department.getName());
        assertEquals(newDepartmentName, department.getName());
    }

    @DisplayName("부서의 상위 본부 변경 테스트")
    @Test
    void editDepartmentHighLevelCenterTest() {

        // given
        int departmentId = addDepartmentForTest();
        int beforeCenterId = departmentRepository.findById(departmentId).getCenterId();

        CmdCenterDTO centerDTO = new CmdCenterDTO();
        centerDTO.setName("SecondCenterNameForTest");
        int afterCenterId = cmdCenterService.addCenter(centerDTO);

        CmdDepartmentDTO departmentDTO = new CmdDepartmentDTO();
        departmentDTO.setId(departmentId);
        departmentDTO.setCenterId(afterCenterId);

        // when
        cmdDepartmentService.editDepartmentHighLevelCenter(departmentDTO);

        // then
        assertNotEquals(beforeCenterId, departmentRepository.findById(departmentId).getCenterId());
        assertEquals(afterCenterId, departmentRepository.findById(departmentId).getCenterId());
    }

    @DisplayName("부서 삭제 테스트")
    @Test
    void removeDepartmentByIdTest() {

        // given
        int departmentId = addDepartmentForTest();
        CmdDepartmentDTO departmentDTO = new CmdDepartmentDTO();
        departmentDTO.setId(departmentId);

        // when
        cmdDepartmentService.removeDepartmentById(departmentDTO);

        // then
        assertNull(departmentRepository.findById(departmentId));
    }

    private int addCenterForTest() {
        CmdCenterDTO centerDTO = new CmdCenterDTO();
        centerDTO.setName("UniqueCenterNameForTest");
        return cmdCenterService.addCenter(centerDTO);
    }

    private int addDepartmentForTest() {
        int centerId = addCenterForTest();
        CmdDepartmentDTO departmentDTO = new CmdDepartmentDTO();
        departmentDTO.setName(TEST_DEPARTMENT_NAME);
        departmentDTO.setCenterId(centerId);
        return cmdDepartmentService.addDepartment(departmentDTO);
    }
}