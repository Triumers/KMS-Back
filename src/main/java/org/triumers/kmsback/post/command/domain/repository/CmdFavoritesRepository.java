package org.triumers.kmsback.post.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdFavorites;

@Repository
public interface CmdFavoritesRepository extends JpaRepository<CmdFavorites, Integer> {
    CmdFavorites findByEmployeeIdAndPostId(Integer employeeId, Integer postId);
}
