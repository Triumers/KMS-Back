package org.triumers.kmsback.organization.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.organization.query.aggregate.entity.QryTeam;
import org.triumers.kmsback.organization.query.dto.QryTeamDTO;
import org.triumers.kmsback.organization.query.repository.OrganizationMapper;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;
import org.triumers.kmsback.user.query.service.QryEmployeeService;

import java.util.ArrayList;
import java.util.List;

@Service
public class QryTeamServiceImpl implements QryTeamService {

    private final OrganizationMapper organizationMapper;
    private final QryDepartmentService qryDepartmentService;
    private final QryEmployeeService qryEmployeeService;

    @Autowired
    public QryTeamServiceImpl(OrganizationMapper organizationMapper,
                              QryDepartmentService qryDepartmentService,
                              @Lazy QryEmployeeService qryEmployeeService) {
        this.organizationMapper = organizationMapper;
        this.qryDepartmentService = qryDepartmentService;
        this.qryEmployeeService = qryEmployeeService;
    }

    @Override
    public QryTeamDTO findQryTeamById(int id) throws WrongInputValueException {
        return teamToDto(organizationMapper.findTeamById(id));
    }

    // 팀원 포함 조회
    @Override
    public QryTeamDTO findQryTeamDetailById(int teamId) throws WrongInputValueException {
        QryTeam team = organizationMapper.findTeamById(teamId);
        if (team == null) {
            throw new WrongInputValueException();
        }
        List<QryEmployeeDTO> teamMembers = qryEmployeeService.findSimpleInfoByTeamId(teamId);
        return new QryTeamDTO(team.getId(), team.getName(), team.getDepartmentId(), null, teamMembers);
    }

    @Override
    public List<QryTeamDTO> findTeamListByName(String teamName) throws WrongInputValueException {

        List<QryTeam> teamList = organizationMapper.findTeamListByName(teamName);
        List<QryTeamDTO> teamDtoList = new ArrayList<>();

        for (QryTeam team : teamList) {
            teamDtoList.add(teamToDto(team));
        }

        return teamDtoList;
    }

    @Override
    public List<QryTeamDTO> findTeamListByDepartmentId(int departmentId) throws WrongInputValueException {
        List<QryTeam> teamList = organizationMapper.findTeamListByDepartmentId(departmentId);
        List<QryTeamDTO> teamDtoList = new ArrayList<>();

        for (QryTeam team : teamList) {
            teamDtoList.add(teamToDto(team));
        }
        return teamDtoList;
    }

    @Override
    public List<QryTeamDTO> findTeamDetailListByDepartmentId(int departmentId) {
        List<QryTeam> teamList = organizationMapper.findTeamListByDepartmentId(departmentId);
        List<QryTeamDTO> teamDtoList = new ArrayList<>();

        for (QryTeam team : teamList) {
            List<QryEmployeeDTO> teamMembers = qryEmployeeService.findSimpleInfoByTeamId(team.getId());
            teamDtoList.add(new QryTeamDTO(team.getId(), team.getName(), team.getDepartmentId(), null, teamMembers));
        }

        return teamDtoList;
    }

    private QryTeamDTO teamToDto(QryTeam qryTeam) throws WrongInputValueException {
        if (qryTeam == null) {
            throw new WrongInputValueException();
        }

        String departmentName = qryDepartmentService.findDepartmentById(qryTeam.getDepartmentId()).getName();

        return new QryTeamDTO(qryTeam.getId(), qryTeam.getName(), qryTeam.getDepartmentId(), departmentName, null);
    }
}
