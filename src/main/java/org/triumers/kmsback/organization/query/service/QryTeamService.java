package org.triumers.kmsback.organization.query.service;

import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.organization.query.dto.QryTeamDTO;

import java.util.List;

public interface QryTeamService {
    QryTeamDTO findQryTeamById(int id) throws WrongInputValueException;

    QryTeamDTO findQryTeamDetailById(int id) throws WrongInputValueException;

    List<QryTeamDTO> findTeamListByName(String teamName) throws WrongInputValueException;

    List<QryTeamDTO> findTeamListByDepartmentId(int departmentId) throws WrongInputValueException;

    List<QryTeamDTO> findTeamDetailListByDepartmentId(int departmentId);
}
