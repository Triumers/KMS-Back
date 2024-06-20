package org.triumers.kmsback.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.triumers.kmsback.organization.command.Application.dto.CmdCenterDTO;
import org.triumers.kmsback.organization.command.Application.dto.CmdDepartmentDTO;
import org.triumers.kmsback.organization.command.Application.dto.CmdTeamDTO;
import org.triumers.kmsback.organization.command.Application.service.CmdCenterService;
import org.triumers.kmsback.organization.command.Application.service.CmdDepartmentService;
import org.triumers.kmsback.organization.command.Application.service.CmdTeamService;
import org.triumers.kmsback.user.command.Application.dto.CmdPositionDTO;
import org.triumers.kmsback.user.command.Application.dto.CmdRankDTO;
import org.triumers.kmsback.user.command.Application.dto.ManageUserDTO;
import org.triumers.kmsback.user.command.Application.service.CmdDutyService;
import org.triumers.kmsback.user.command.Application.service.ManagerService;
import org.triumers.kmsback.user.command.domain.repository.EmployeeRepository;
import org.triumers.kmsback.user.command.domain.service.CustomUserDetailsService;

@Component
public class LoggedInUser {
    private final String RIGHT_FORMAT_EMAIL = TestUserInfo.EMAIL;
    private final String RIGHT_FORMAT_PASSWORD = TestUserInfo.PASSWORD;
    private final String RIGHT_FORMAT_NAME = TestUserInfo.NAME;
    private final String RIGHT_FORMAT_USER_ROLE = TestUserInfo.USER_ROLE;
    private final String RIGHT_PHONE_NUMBER = TestUserInfo.PHONE_NUMBER;
    private final String HR_MANAGER_EMAIL = TestUserInfo.HR_MANAGER_EMAIL;
    private final String HR_MANAGER_ROLE = TestUserInfo.HR_MANAGER_ROLE;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CmdCenterService cmdCenterService;

    @Autowired
    private CmdDepartmentService cmdDepartmentService;

    @Autowired
    private CmdTeamService cmdTeamService;

    @Autowired
    private CmdDutyService cmdDutyService;

    // 테스트용 본부 생성
    private int addCenterForTest() {
        CmdCenterDTO centerDTO = new CmdCenterDTO();
        centerDTO.setName("UniqueCenterNameForTest");
        return cmdCenterService.addCenter(centerDTO);
    }

    // 테스트용 부서 생성
    private int addDepartmentForTest() {
        int centerId = addCenterForTest();
        CmdDepartmentDTO departmentDTO = new CmdDepartmentDTO();
        departmentDTO.setName("UniqDepartmentNameForTest");
        departmentDTO.setCenterId(centerId);
        return cmdDepartmentService.addDepartment(departmentDTO);
    }

    // 테스트용 팀 생성
    private int addTeamForTest() {
        int departmentId = addDepartmentForTest();
        CmdTeamDTO teamDTO = new CmdTeamDTO();
        teamDTO.setName("UniqTeamNameForTest");
        teamDTO.setDepartmentId(departmentId);

        return cmdTeamService.addTeam(teamDTO);
    }

    // 테스트용 직책 생성
    private int addPositionForTest() {
        CmdPositionDTO positionDTO = new CmdPositionDTO();
        positionDTO.setName("UniqPositionNameForTest");

        return cmdDutyService.addPosition(positionDTO);
    }

    // 테스트용 직급 생성
    private int addRankForTest() {
        CmdRankDTO rankDTO = new CmdRankDTO();
        rankDTO.setName("UniqRankNameForTest");

        return cmdDutyService.addRank(rankDTO);
    }

    // 테스트용 계정 DTO 생성
    private ManageUserDTO createRightAuthDTO() {
        int teamId = addTeamForTest();
        int positionId = addPositionForTest();
        int rankId = addRankForTest();

        return new ManageUserDTO(RIGHT_FORMAT_EMAIL, RIGHT_FORMAT_PASSWORD, RIGHT_FORMAT_NAME, null,
                RIGHT_FORMAT_USER_ROLE, null, null, RIGHT_PHONE_NUMBER, teamId, positionId,
                rankId);
    }

    // 테스트용 관리자 계정 DTO 생성
    private ManageUserDTO createHrManagerDTO() {
        return new ManageUserDTO(HR_MANAGER_EMAIL, RIGHT_FORMAT_PASSWORD, RIGHT_FORMAT_NAME, null,
                HR_MANAGER_ROLE, null, null, RIGHT_PHONE_NUMBER, 1, 1,
                1);
    }

    // 테스트용 계정 회원가입
    private void setUser() {
        managerService.signup(createRightAuthDTO());
    }

    // 테스트용 관리자 계정 회원가입
    private void setHrManager() {
        managerService.signup(createHrManagerDTO());
    }

    // 테스트용 계정으로 로그인
    public void setting() {
        setUser();
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(employeeRepository);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(RIGHT_FORMAT_EMAIL);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }

    // 테스트용 관리자 계정으로 로그인
    public void settingHrManager() {
        setHrManager();
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(employeeRepository);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(HR_MANAGER_EMAIL);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }
}
