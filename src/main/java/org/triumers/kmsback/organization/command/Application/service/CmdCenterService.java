package org.triumers.kmsback.organization.command.Application.service;

import org.triumers.kmsback.organization.command.Application.dto.CmdCenterDTO;

public interface CmdCenterService {

    int addCenter(CmdCenterDTO cmdCenterDTO);

    void editCenterName(CmdCenterDTO centerDTO);

    void removeCenter(CmdCenterDTO centerDTO);
}
