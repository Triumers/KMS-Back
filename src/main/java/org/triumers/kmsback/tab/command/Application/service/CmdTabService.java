package org.triumers.kmsback.tab.command.Application.service;

import org.triumers.kmsback.tab.command.Application.dto.CmdJoinEmployeeDTO;
import org.triumers.kmsback.tab.command.Application.dto.CmdTabRelationDTO;

public interface CmdTabService {
    CmdJoinEmployeeDTO addEmployeeTab(CmdJoinEmployeeDTO employee);

    CmdJoinEmployeeDTO deleteEmployeeTab(CmdJoinEmployeeDTO employee);

    CmdTabRelationDTO registTab(CmdTabRelationDTO tabRelation, int employeeId);

    String getTabName(int id);
}
