package com.netflix_clone.boardservice.repository.commentRepository;

import com.netflix_clone.boardservice.repository.domains.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}
