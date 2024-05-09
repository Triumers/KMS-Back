package org.triumers.kmsback.post.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdLike;

@Repository
public interface CmdLikeRepository extends JpaRepository<CmdLike, Integer> {

    CmdLike findByEmployeeIdAndPostId(Integer employeeId, Integer postId);
}
