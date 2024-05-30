package org.triumers.kmsback.user.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.user.query.aggregate.entity.QryPosition;
import org.triumers.kmsback.user.query.aggregate.entity.QryRank;
import org.triumers.kmsback.user.query.dto.QryPositionDTO;
import org.triumers.kmsback.user.query.dto.QryRankDTO;
import org.triumers.kmsback.user.query.repository.DutyMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class QryDutyServiceImpl implements QryDutyService {

    private final DutyMapper dutyMapper;

    @Autowired
    public QryDutyServiceImpl(DutyMapper dutyMapper) {
        this.dutyMapper = dutyMapper;
    }

    @Override
    public List<QryPositionDTO> findAllPosition() {

        List<QryPosition> positionList = dutyMapper.findAllPosition();
        List<QryPositionDTO> positionDTOList = new ArrayList<>();

        for (QryPosition position : positionList) {
            positionDTOList.add(new QryPositionDTO(position.getId(), position.getName()));
        }

        return positionDTOList;
    }

    @Override
    public List<QryPositionDTO> findPositionByName(String name) {

        List<QryPosition> positionList = dutyMapper.findPositionListByName(name);
        List<QryPositionDTO> positionDTOList = new ArrayList<>();

        for (QryPosition position : positionList) {
            positionDTOList.add(new QryPositionDTO(position.getId(), position.getName()));
        }

        return positionDTOList;
    }

    @Override
    public List<QryRankDTO> findAllRank() {

        List<QryRank> rankList = dutyMapper.findAllRank();
        List<QryRankDTO> rankDTOList = new ArrayList<>();

        for (QryRank rank : rankList) {
            rankDTOList.add(new QryRankDTO(rank.getId(), rank.getName()));
        }

        return rankDTOList;
    }

    @Override
    public List<QryRankDTO> findRankByName(String name) {

        List<QryRank> rankList = dutyMapper.findRankListByName(name);
        List<QryRankDTO> rankDTOList = new ArrayList<>();

        for (QryRank rank : rankList) {
            rankDTOList.add(new QryRankDTO(rank.getId(), rank.getName()));
        }

        return rankDTOList;
    }
}
