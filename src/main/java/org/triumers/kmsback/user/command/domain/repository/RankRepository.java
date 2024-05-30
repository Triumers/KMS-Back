package org.triumers.kmsback.user.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.user.command.domain.aggregate.entity.CmdRank;

@Repository
public interface RankRepository extends JpaRepository<CmdRank, Integer> {

    CmdRank findById(int id);
}
