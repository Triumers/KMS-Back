package org.triumers.kmsback.post.command.Application.service;

import org.triumers.kmsback.post.command.Application.dto.CmdJoinEmployeeDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdTabRelationDTO;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdJoinEmployee;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdTabRelation;

public interface CmdTabService {
    CmdJoinEmployee addEmployeeTab(CmdJoinEmployeeDTO employee);

    CmdJoinEmployee deleteEmployeeTab(CmdJoinEmployeeDTO employee);

    CmdTabRelation registTab(CmdTabRelationDTO tabRelation, int employeeId);
}
