package org.triumers.kmsback.common.auth.createAdmin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.organization.command.Application.dto.CmdCenterDTO;
import org.triumers.kmsback.organization.command.Application.dto.CmdDepartmentDTO;
import org.triumers.kmsback.organization.command.Application.dto.CmdTeamDTO;
import org.triumers.kmsback.organization.command.Application.service.CmdCenterService;
import org.triumers.kmsback.organization.command.Application.service.CmdDepartmentService;
import org.triumers.kmsback.organization.command.Application.service.CmdTeamService;
import org.triumers.kmsback.organization.query.dto.QryCenterDTO;
import org.triumers.kmsback.organization.query.dto.QryDepartmentDTO;
import org.triumers.kmsback.organization.query.dto.QryTeamDTO;
import org.triumers.kmsback.organization.query.service.QryCenterService;
import org.triumers.kmsback.organization.query.service.QryDepartmentService;
import org.triumers.kmsback.organization.query.service.QryTeamService;
import org.triumers.kmsback.user.command.Application.dto.CmdPositionDTO;
import org.triumers.kmsback.user.command.Application.dto.CmdRankDTO;
import org.triumers.kmsback.user.command.Application.dto.ManageUserDTO;
import org.triumers.kmsback.user.command.Application.service.CmdDutyService;
import org.triumers.kmsback.user.command.Application.service.ManagerService;
import org.triumers.kmsback.user.query.dto.QryPositionDTO;
import org.triumers.kmsback.user.query.dto.QryRankDTO;
import org.triumers.kmsback.user.query.service.QryDutyService;
import org.triumers.kmsback.user.query.service.QryEmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AdminAccountInitializer implements CommandLineRunner {

    private final QryEmployeeService qryEmployeeService;
    private final QryDutyService qryDutyService;
    private final CmdDutyService cmdDutyService;
    private final QryTeamService qryTeamService;
    private final CmdTeamService cmdTeamService;
    private final QryDepartmentService qryDepartmentService;
    private final CmdDepartmentService cmdDepartmentService;
    private final QryCenterService qryCenterService;
    private final CmdCenterService cmdCenterService;
    private final ManagerService managerService;

    private final String adminEmail;
    private final String adminName;
    private final String adminPassword;
    private final String adminRole;
    private final Map<String, Integer> adminInfo = new HashMap<>();

    public AdminAccountInitializer(QryEmployeeService qryEmployeeService, QryDutyService qryDutyService, CmdDutyService cmdDutyService, QryTeamService qryTeamService, CmdTeamService cmdTeamService, QryDepartmentService qryDepartmentService, CmdDepartmentService cmdDepartmentService, QryCenterService qryCenterService, CmdCenterService cmdCenterService, ManagerService managerService,
                                   @Value("${admin.email}") String adminEmail,
                                   @Value("${admin.name}") String adminName,
                                   @Value("${admin.password}") String adminPassword,
                                   @Value("${admin.role}") String adminRole) {
        this.qryEmployeeService = qryEmployeeService;
        this.qryDutyService = qryDutyService;
        this.cmdDutyService = cmdDutyService;
        this.qryTeamService = qryTeamService;
        this.cmdTeamService = cmdTeamService;
        this.qryDepartmentService = qryDepartmentService;
        this.cmdDepartmentService = cmdDepartmentService;
        this.qryCenterService = qryCenterService;
        this.cmdCenterService = cmdCenterService;
        this.managerService = managerService;
        this.adminEmail = adminEmail;
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.adminRole = adminRole;
    }

    @Override
    public void run(String... args) throws Exception {

        try {
            qryEmployeeService.findEmployeeByEmail(adminEmail);
            System.out.println("admin-account is exist");
        } catch (NullPointerException e) {
            System.out.println("create admin-account start");
            settingAdminInfo();
            ManageUserDTO admin = new ManageUserDTO(adminEmail, adminPassword, adminName, null, adminRole,
                    null, null, null, adminInfo.get("Team"), adminInfo.get("Position"),
                    adminInfo.get("Rank"));
            managerService.signup(admin);
            System.out.println("create admin-account end");
        }
    }

    private void settingAdminInfo() {
        settingAdminDuty();
        settingTeam();
    }

    private void settingAdminDuty() {

        List<QryPositionDTO> position = qryDutyService.findPositionByName("관리자");
        if (!position.isEmpty()) {
            adminInfo.put("Position", position.get(0).getId());
        } else {
            adminInfo.put("Position", cmdDutyService.addPosition(new CmdPositionDTO(0, "관리자")));
        }

        List<QryRankDTO> rank = qryDutyService.findRankByName("관리자");
        if (!rank.isEmpty()) {
            adminInfo.put("Rank", rank.get(0).getId());
        } else {
            adminInfo.put("Rank", cmdDutyService.addRank(new CmdRankDTO(0, "관리자")));
        }
    }

    private void settingTeam() {

        try {
            List<QryTeamDTO> team = qryTeamService.findTeamListByName("관리자");
            adminInfo.put("Team", team.get(0).getId());

        // 조회되는 팀이 없는 경우 발생하는 Exception
        } catch (WrongInputValueException | IndexOutOfBoundsException e) {
            adminInfo.put("Team", cmdTeamService.addTeam(new CmdTeamDTO(0, "관리자", settingDepartment())));
        }
    }

    private int settingDepartment() {

        List<QryDepartmentDTO> department = qryDepartmentService.findDepartmentListByName("관리자");

        if (!department.isEmpty()) {
            return department.get(0).getId();
        }

        return cmdDepartmentService.addDepartment(new CmdDepartmentDTO(0, "관리자", settingCenter()));
    }

    private int settingCenter() {

        List<QryCenterDTO> center = qryCenterService.findCenterListByName("관리자");

        if (!center.isEmpty()) {
            return center.get(0).getId();
        }

        return cmdCenterService.addCenter(new CmdCenterDTO(0, "관리자"));
    }
}