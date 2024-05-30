package org.triumers.kmsback.user.command.Application.service;

import org.triumers.kmsback.user.command.Application.dto.CmdPositionDTO;
import org.triumers.kmsback.user.command.Application.dto.CmdRankDTO;

public interface CmdDutyService {

    int addPosition(CmdPositionDTO positionDTO);

    void editPositionName(CmdPositionDTO positionDTO);

    void removePosition(CmdPositionDTO positionDTO);

    int addRank(CmdRankDTO rankDTO);

    void editRankName(CmdRankDTO rankDTO);

    void removeRank(CmdRankDTO rankDTO);
}
