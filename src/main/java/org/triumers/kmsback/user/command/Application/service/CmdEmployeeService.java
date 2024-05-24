package org.triumers.kmsback.user.command.Application.service;

import org.triumers.kmsback.user.command.Application.dto.CmdEmployeeDTO;

import java.util.List;

public interface CmdEmployeeService {

    CmdEmployeeDTO findEmployeeById(int id);

    CmdEmployeeDTO findEmployeeByEmail(String email);

    List<CmdEmployeeDTO> findEmployeeByName(String name);
}
