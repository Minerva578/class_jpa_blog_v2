package com.example.blog_v1.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    /*
     * 게시글 조회 메서드
     * @param id 조회할 게시글 ID
     * @return 조회된 Borad 엔티티, 존재하지 않으면 null 반환
     * */
    public Board findById(int id) {
        return em.find(Board.class, id);
    }
    /*
     * JPQL -> Fetch 조인 사용 - 성능 최적화
     * 한방에 쿼리를 사용해서 즉, 직접 조인해서 데이터를 가져 옵니다.
     * @param id
     * return
     * */
    public Board findbyIdJoinUser(int id) {
        // JPQL -> Fetch join을 사용해 보자.
        String jpql = "select b from board_tb b join fetch b.user where b.id = :id";
        return em.createQuery(jpql, Board.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    /*
    * 모든 게시글 조회
    * @return 게시글 리스트
    * */
    public List<Board> findAll() {
        TypedQuery<Board> jpql = em.createQuery(" SELECT b FROM board_tb b ORDER BY b.id DESC ", Board.class);
        return jpql.getResultList();
    }
}
