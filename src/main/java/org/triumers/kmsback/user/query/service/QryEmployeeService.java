package org.triumers.kmsback.user.query.service;

import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;

import java.util.List;

public interface QryEmployeeService {

    QryEmployeeDTO findEmployeeById(int id);

    List<QryEmployeeDTO> findAllEmployee();

    QryEmployeeDTO findEmployeeByEmail(String email);

    List<QryEmployeeDTO> findEmployeeByName(String name);

    List<QryEmployeeDTO> findEmployeeByTeamId(int teamId);
}
