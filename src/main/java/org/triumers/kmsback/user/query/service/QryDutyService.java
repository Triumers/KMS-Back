package org.triumers.kmsback.user.query.service;

import org.triumers.kmsback.user.query.dto.QryPositionDTO;
import org.triumers.kmsback.user.query.dto.QryRankDTO;

import java.util.List;

public interface QryDutyService {

    QryPositionDTO findPositionById(int id);

    List<QryPositionDTO> findAllPosition();

    List<QryPositionDTO> findPositionByName(String name);

    QryRankDTO findRankById(int id);

    List<QryRankDTO> findAllRank();

    List<QryRankDTO> findRankByName(String name);
}
