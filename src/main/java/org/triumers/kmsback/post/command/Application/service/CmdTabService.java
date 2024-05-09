package org.triumers.kmsback.post.command.Application.service;

import org.triumers.kmsback.post.command.Application.dto.CmdJoinEmployeeDTO;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdJoinEmployee;

public interface CmdTabService {
    CmdJoinEmployee addEmployeeTab(CmdJoinEmployeeDTO employee);

    CmdJoinEmployee deleteEmployeeTab(CmdJoinEmployeeDTO employee);
}
