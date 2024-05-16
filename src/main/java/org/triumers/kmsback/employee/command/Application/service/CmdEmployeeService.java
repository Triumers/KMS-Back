package org.triumers.kmsback.employee.command.Application.service;

import org.triumers.kmsback.employee.command.Application.dto.CmdEmployeeDTO;

public interface CmdEmployeeService {

    CmdEmployeeDTO findEmployeeById(int id);

    CmdEmployeeDTO findEmployeeByEmail(String email);

    CmdEmployeeDTO findEmployeeByName(String name);
}
