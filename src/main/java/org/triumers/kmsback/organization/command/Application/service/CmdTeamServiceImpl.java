package org.triumers.kmsback.organization.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.organization.command.Application.dto.CmdTeamDTO;
import org.triumers.kmsback.organization.command.domain.aggregate.entity.CmdTeam;
import org.triumers.kmsback.organization.command.domain.repository.TeamRepository;

@Service
public class CmdTeamServiceImpl implements CmdTeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public CmdTeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public int addTeam(CmdTeamDTO teamDTO) {
        return teamRepository.save(new CmdTeam(teamDTO.getId(), teamDTO.getName(), teamDTO.getDepartmentId())).getId();
    }

    @Override
    public void editTeamName(CmdTeamDTO teamDTO) {
        CmdTeam team = teamRepository.findById(teamDTO.getId());
        team.setName(teamDTO.getName());
        teamRepository.save(team);
    }

    @Override
    public void editTeamHighLevelDepartment(CmdTeamDTO teamDTO) {
        CmdTeam team = teamRepository.findById(teamDTO.getId());
        team.setDepartmentId(teamDTO.getDepartmentId());
        teamRepository.save(team);
    }

    @Override
    public void removeTeamById(CmdTeamDTO teamDTO) {
        CmdTeam team = teamRepository.findById(teamDTO.getId());
        teamRepository.delete(team);
    }
}
