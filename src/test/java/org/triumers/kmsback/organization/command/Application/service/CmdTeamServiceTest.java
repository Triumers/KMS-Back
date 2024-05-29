package org.triumers.kmsback.organization.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.organization.command.Application.dto.CmdCenterDTO;
import org.triumers.kmsback.organization.command.Application.dto.CmdDepartmentDTO;
import org.triumers.kmsback.organization.command.Application.dto.CmdTeamDTO;
import org.triumers.kmsback.organization.command.domain.aggregate.entity.CmdTeam;
import org.triumers.kmsback.organization.command.domain.repository.DepartmentRepository;
import org.triumers.kmsback.organization.command.domain.repository.TeamRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CmdTeamServiceTest {

    private final String TEST_TEAM_NAME = "UniqTeamNameForTest";

    @Autowired
    private CmdTeamService cmdTeamService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CmdDepartmentService cmdDepartmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CmdCenterService cmdCenterService;

    @DisplayName("팀 생성 테스트")
    @Test
    void addTeamTest() {

        // given
        int departmentId = addDepartmentForTest();
        CmdTeamDTO cmdTeamDTO = new CmdTeamDTO();
        cmdTeamDTO.setName(TEST_TEAM_NAME);
        cmdTeamDTO.setDepartmentId(departmentId);

        // when
        int teamId = cmdTeamService.addTeam(cmdTeamDTO);
        CmdTeam team = teamRepository.findById(teamId);

        // then
        assertNotNull(team);
        assertEquals(TEST_TEAM_NAME, team.getName());
        assertEquals(departmentId, team.getDepartmentId());
    }

    @DisplayName("팀명 수정 테스트")
    @Test
    void editTeamNameTest() {

        // given
        int teamId = addTeamForTest();
        String newTeamName = "새로운 팀 이름";
        CmdTeamDTO cmdTeamDTO = new CmdTeamDTO();
        cmdTeamDTO.setId(teamId);
        cmdTeamDTO.setName(newTeamName);

        // when
        cmdTeamService.editTeamName(cmdTeamDTO);
        CmdTeam team = teamRepository.findById(teamId);

        // then
        assertEquals(newTeamName, team.getName());
        assertNotEquals(TEST_TEAM_NAME, team.getName());
    }

    @DisplayName("팀의 상위 부서 변경 테스트")
    @Test
    void editTeamHighLevelDepartmentTest() {

        // given
        int teamId = addTeamForTest();
        int beforeDepartmentId = teamRepository.findById(teamId).getDepartmentId();

        CmdDepartmentDTO cmdDepartmentDTO = new CmdDepartmentDTO();
        cmdDepartmentDTO.setCenterId(departmentRepository.findById(beforeDepartmentId).getCenterId());
        cmdDepartmentDTO.setName("SecondDepartmentNameForTest");
        int afterDepartmentId = departmentRepository.findById(cmdDepartmentService.addDepartment(cmdDepartmentDTO)).getId();

        CmdTeamDTO cmdTeamDTO = new CmdTeamDTO();
        cmdTeamDTO.setId(teamId);
        cmdTeamDTO.setDepartmentId(afterDepartmentId);

        // when
        cmdTeamService.editTeamHighLevelDepartment(cmdTeamDTO);

        // then
        assertNotEquals(beforeDepartmentId, teamRepository.findById(teamId).getDepartmentId());
        assertEquals(afterDepartmentId, teamRepository.findById(teamId).getDepartmentId());
    }

    @DisplayName("팀 삭제 테스트")
    @Test
    void removeTeamByIdTest() {

        // given
        int teamId = addTeamForTest();
        CmdTeamDTO cmdTeamDTO = new CmdTeamDTO();
        cmdTeamDTO.setId(teamId);

        // when
        cmdTeamService.removeTeamById(cmdTeamDTO);

        // then
        assertNull(teamRepository.findById(teamId));
    }

    private int addCenterForTest() {
        CmdCenterDTO centerDTO = new CmdCenterDTO();
        centerDTO.setName("UniqueCenterNameForTest");
        return cmdCenterService.addCenter(centerDTO);
    }

    private int addDepartmentForTest() {
        int centerId = addCenterForTest();
        CmdDepartmentDTO departmentDTO = new CmdDepartmentDTO();
        departmentDTO.setName("UniqDepartmentNameForTest");
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
}