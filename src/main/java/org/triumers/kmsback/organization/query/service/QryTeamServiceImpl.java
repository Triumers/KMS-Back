package org.triumers.kmsback.organization.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.organization.query.aggregate.entity.QryTeam;
import org.triumers.kmsback.organization.query.dto.QryTeamDTO;
import org.triumers.kmsback.organization.query.repository.OrganizationMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class QryTeamServiceImpl implements QryTeamService {

    private final OrganizationMapper organizationMapper;
    private final QryDepartmentService qryDepartmentService;

    @Autowired
    public QryTeamServiceImpl(OrganizationMapper organizationMapper, QryDepartmentService qryDepartmentService) {
        this.organizationMapper = organizationMapper;
        this.qryDepartmentService = qryDepartmentService;
    }

    @Override
    public QryTeamDTO findQryTeamById(int id) {
        return teamToDto(organizationMapper.findTeamById(id));
    }

    @Override
    public List<QryTeamDTO> findTeamListByName(String teamName) {

        List<QryTeam> teamList = organizationMapper.findTeamListByName(teamName);
        List<QryTeamDTO> teamDtoList = new ArrayList<>();

        for (QryTeam team : teamList) {
            teamDtoList.add(teamToDto(team));
        }

        return teamDtoList;
    }

    @Override
    public List<QryTeamDTO> findTeamListByDepartment(int departmentId) {
        List<QryTeam> teamList = organizationMapper.findTeamListByDepartmentId(departmentId);
        List<QryTeamDTO> teamDtoList = new ArrayList<>();

        for (QryTeam team : teamList) {
            teamDtoList.add(teamToDto(team));
        }
        return teamDtoList;
    }

    private QryTeamDTO teamToDto(QryTeam qryTeam) {
        String departmentName = qryDepartmentService.findDepartmentById(qryTeam.getDepartmentId()).getName();
        return new QryTeamDTO(qryTeam.getId(), qryTeam.getName(), qryTeam.getDepartmentId(), departmentName);
    }
}
