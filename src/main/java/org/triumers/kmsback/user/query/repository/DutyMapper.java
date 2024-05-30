package org.triumers.kmsback.user.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.triumers.kmsback.user.query.aggregate.entity.QryPosition;
import org.triumers.kmsback.user.query.aggregate.entity.QryRank;

import java.util.List;

@Mapper
public interface DutyMapper {

    QryPosition findPositionById(int id);

    List<QryPosition> findAllPosition();

    List<QryPosition> findPositionListByName(String positionName);

    QryRank findRankById(int id);

    List<QryRank> findAllRank();

    List<QryRank> findRankListByName(String rankName);
}
