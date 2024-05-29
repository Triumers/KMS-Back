package org.triumers.kmsback.user.command.Application.service;

import org.triumers.kmsback.user.command.Application.dto.CmdPositionDTO;

public interface CmdDutyService {

    int addPosition(CmdPositionDTO positionDTO);

    void editPositionName(CmdPositionDTO positionDTO);

    void removePosition(CmdPositionDTO positionDTO);
}
