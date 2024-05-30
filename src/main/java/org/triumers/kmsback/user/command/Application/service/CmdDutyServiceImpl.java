package org.triumers.kmsback.user.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.user.command.Application.dto.CmdPositionDTO;
import org.triumers.kmsback.user.command.Application.dto.CmdRankDTO;
import org.triumers.kmsback.user.command.domain.aggregate.entity.CmdPosition;
import org.triumers.kmsback.user.command.domain.aggregate.entity.CmdRank;
import org.triumers.kmsback.user.command.domain.repository.PositionRepository;
import org.triumers.kmsback.user.command.domain.repository.RankRepository;

@Service
public class CmdDutyServiceImpl implements CmdDutyService {

    private final PositionRepository positionRepository;
    private final RankRepository rankRepository;

    @Autowired
    public CmdDutyServiceImpl(PositionRepository positionRepository, RankRepository rankRepository) {
        this.positionRepository = positionRepository;
        this.rankRepository = rankRepository;
    }

    @Override
    public int addPosition(CmdPositionDTO positionDTO) {
        return positionRepository.save(new CmdPosition(positionDTO.getId(), positionDTO.getName())).getId();
    }

    @Override
    public void editPositionName(CmdPositionDTO positionDTO) {
        CmdPosition position = positionRepository.findById(positionDTO.getId());
        position.setName(positionDTO.getName());
        positionRepository.save(position);
    }

    @Override
    public void removePosition(CmdPositionDTO positionDTO) {
        CmdPosition position = positionRepository.findById(positionDTO.getId());
        positionRepository.delete(position);
    }

    @Override
    public int addRank(CmdRankDTO rankDTO) {
        return rankRepository.save(new CmdRank(rankDTO.getId(), rankDTO.getName())).getId();
    }

    @Override
    public void editRankName(CmdRankDTO rankDTO) {
        CmdRank rank = rankRepository.findById(rankDTO.getId());
        rank.setName(rankDTO.getName());
        rankRepository.save(rank);
    }

    @Override
    public void removeRank(CmdRankDTO rankDTO) {
        CmdRank rank = rankRepository.findById(rankDTO.getId());
        rankRepository.delete(rank);
    }
}
