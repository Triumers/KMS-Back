package org.triumers.kmsback.common.translation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.common.translation.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
