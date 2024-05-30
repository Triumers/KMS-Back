package org.triumers.kmsback.organization.query.service;

import org.triumers.kmsback.organization.query.dto.QryTeamDTO;

import java.util.List;

public interface QryTeamService {
    QryTeamDTO findQryTeamById(int id);

    List<QryTeamDTO> findTeamListByName(String teamName);

    List<QryTeamDTO> findTeamListByDepartment(int departmentId);
}
