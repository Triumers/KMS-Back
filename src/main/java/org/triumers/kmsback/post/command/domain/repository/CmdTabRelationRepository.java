package org.triumers.kmsback.post.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdTabRelation;

@Repository
public interface CmdTabRelationRepository extends JpaRepository<CmdTabRelation, Integer> {
}
