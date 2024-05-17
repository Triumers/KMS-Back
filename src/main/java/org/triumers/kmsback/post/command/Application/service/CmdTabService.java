package org.triumers.kmsback.post.command.Application.service;

import org.triumers.kmsback.post.command.Application.dto.CmdJoinEmployeeDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdTabDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdTabRelationDTO;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdJoinEmployee;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdTabRelation;

public interface CmdTabService {
    CmdJoinEmployeeDTO addEmployeeTab(CmdJoinEmployeeDTO employee);

    CmdJoinEmployeeDTO deleteEmployeeTab(CmdJoinEmployeeDTO employee);

    CmdTabRelationDTO registTab(CmdTabRelationDTO tabRelation, int employeeId);
}
