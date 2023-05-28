package com.netflix_clone.boardservice.repository.boardProfileRepository;

import com.netflix_clone.boardservice.repository.domains.BoardProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardProfileRepository extends JpaRepository<BoardProfile, Long> {
}
