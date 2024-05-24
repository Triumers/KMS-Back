package org.triumers.kmsback.employee.command.Application.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.triumers.kmsback.employee.command.Application.dto.CmdEmployeeDTO;

@Qualifier("cmdEmployeeService")
public interface CmdEmployeeService {

    CmdEmployeeDTO findEmployeeById(int id);

    CmdEmployeeDTO findEmployeeByEmail(String email);

    CmdEmployeeDTO findEmployeeByName(String name);
}
