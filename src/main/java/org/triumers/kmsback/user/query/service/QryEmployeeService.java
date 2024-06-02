package org.triumers.kmsback.user.query.service;

import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;

import java.util.List;

public interface QryEmployeeService {

    QryEmployeeDTO findEmployeeById(int id) throws WrongInputValueException;

    List<QryEmployeeDTO> findAllEmployee() throws WrongInputValueException;

    QryEmployeeDTO findEmployeeByEmail(String email) throws WrongInputValueException;

    List<QryEmployeeDTO> findEmployeeByName(String name) throws WrongInputValueException;

    List<QryEmployeeDTO> findEmployeeByTeamId(int teamId) throws WrongInputValueException;

    List<QryEmployeeDTO> findSimpleInfoByTeamId(int teamId);
}
