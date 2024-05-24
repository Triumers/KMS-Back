package org.triumers.kmsback.employee.command.Application.service;

import org.triumers.kmsback.employee.command.Application.dto.CmdEmployeeDTO;

import java.util.List;

public interface CmdEmployeeService {

    CmdEmployeeDTO findEmployeeById(int id);

    CmdEmployeeDTO findEmployeeByEmail(String email);

    List<CmdEmployeeDTO> findEmployeeByName(String name);
}
