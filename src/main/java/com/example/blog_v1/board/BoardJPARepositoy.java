package com.example.blog_v1.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardJPARepositoy extends JpaRepository<Board, Integer> {

    // 커스텀 쿼리 메서드
    // Board 와 User 엔티티를 조인하여 특정 Board 엔티티를 조회
    @Query("select b from board_tb b join fetch b.user u where b.id = :id")
    Optional<Board> findByIdJoinUser(@Param("id") int id);
}
