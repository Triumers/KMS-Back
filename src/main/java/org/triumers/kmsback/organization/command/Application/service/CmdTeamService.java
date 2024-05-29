package org.triumers.kmsback.organization.command.Application.service;

import org.triumers.kmsback.organization.command.Application.dto.CmdTeamDTO;

public interface CmdTeamService {

    int addTeam(CmdTeamDTO teamDTO);

    void editTeamName(CmdTeamDTO teamDTO);

    void editTeamHighLevelDepartment(CmdTeamDTO teamDTO);

    void removeTeamById(CmdTeamDTO teamDTO);
}
