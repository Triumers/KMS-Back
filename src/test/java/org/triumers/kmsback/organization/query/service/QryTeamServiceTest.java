package org.triumers.kmsback.organization.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.organization.command.Application.dto.CmdCenterDTO;
import org.triumers.kmsback.organization.command.Application.dto.CmdDepartmentDTO;
import org.triumers.kmsback.organization.command.Application.dto.CmdTeamDTO;
import org.triumers.kmsback.organization.command.Application.service.CmdCenterService;
import org.triumers.kmsback.organization.command.Application.service.CmdDepartmentService;
import org.triumers.kmsback.organization.command.Application.service.CmdTeamService;
import org.triumers.kmsback.organization.query.dto.QryTeamDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QryTeamServiceTest {

    private final String TEST_TEAM_NAME = "UniqTeamName";

    @Autowired
    private CmdCenterService cmdCenterService;

    @Autowired
    private CmdDepartmentService cmdDepartmentService;

    @Autowired
    private QryDepartmentService qryDepartmentService;

    @Autowired
    private CmdTeamService cmdTeamService;

    @Autowired
    private QryTeamService qryTeamService;

    @DisplayName("팀 검색 by Id")
    @Test
    void findQryTeamById() {

        // given
        int teamId = addTeamForTest();

        // when
        QryTeamDTO qryTeamDTO = qryTeamService.findQryTeamById(teamId);

        // then
        assertNotNull(qryTeamDTO);
        assertEquals(qryTeamDTO.getName(), TEST_TEAM_NAME);
        assertEquals(qryTeamDTO.getDepartmentName(), qryDepartmentService.findDepartmentById(qryTeamDTO.getDepartmentId()).getName());
    }

    @DisplayName("팀 검색 by 팀명")
    @Test
    void findTeamListByName() {

        // given
        int departmentId = addDepartmentForTest();

        List<String> names = new ArrayList<>();
        String target = "UniqWord";

        names.add(target);
        names.add(target + "테스트");
        names.add("테스트" + target);
        names.add("테스트" + target + "테스트");

        for (String name : names) {
            addTeamForTest(departmentId, name);
        }

        // when
        List<QryTeamDTO> resultList = qryTeamService.findTeamListByName(target);

        // then
        for (QryTeamDTO result : resultList) {  // 결과가 검색어를 포함하는지 여부
            assertTrue(result.getName().contains(target));
        }

        assertTrue(resultList.size() >= names.size());  // 테스트용으로 추가한 값 이상으로 검색되는지
    }

    @DisplayName("팀 검색 by 부서")
    @Test
    void findTeamListByDepartment() {

        // given
        int departmentId = addDepartmentForTest();

        List<String> names = new ArrayList<>();

        names.add(TEST_TEAM_NAME);
        names.add(TEST_TEAM_NAME + "테스트");
        names.add("테스트" + TEST_TEAM_NAME);
        names.add("테스트" + TEST_TEAM_NAME + "테스트");

        for (String name : names) {
            addTeamForTest(departmentId, name);
        }

        // when
        List<QryTeamDTO> resultList = qryTeamService.findTeamListByDepartment(departmentId);

        // then
        for (QryTeamDTO result : resultList) {  // 결과가 검색어를 포함하는지 여부
            assertTrue(result.getName().contains(TEST_TEAM_NAME));
        }

        assertTrue(resultList.size() >= names.size());  // 테스트용으로 추가한 값 이상으로 검색되는지
    }

    private int addCenterForTest() {
        CmdCenterDTO cmdCenterDTO = new CmdCenterDTO();
        cmdCenterDTO.setName("UniqCenterName");
        return cmdCenterService.addCenter(cmdCenterDTO);
    }

    private int addDepartmentForTest() {
        int centerId = addCenterForTest();
        CmdDepartmentDTO departmentDTO = new CmdDepartmentDTO();
        departmentDTO.setName("UniqDepartmentName");
        departmentDTO.setCenterId(centerId);
        return cmdDepartmentService.addDepartment(departmentDTO);
    }

    private int addTeamForTest() {
        int departmentId = addDepartmentForTest();
        CmdTeamDTO teamDTO = new CmdTeamDTO();
        teamDTO.setName(TEST_TEAM_NAME);
        teamDTO.setDepartmentId(departmentId);
        return cmdTeamService.addTeam(teamDTO);
    }

    private void addTeamForTest(int departmentId, String name) {
        CmdTeamDTO teamDTO = new CmdTeamDTO();
        teamDTO.setName(name);
        teamDTO.setDepartmentId(departmentId);
        cmdTeamService.addTeam(teamDTO);
    }
}