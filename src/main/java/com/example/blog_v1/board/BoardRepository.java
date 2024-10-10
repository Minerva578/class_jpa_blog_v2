package com.example.blog_v1.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    // em.persist(board) -> 비영속 상태인 엔티티를 영속상태로 전환
    @Transactional
    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    /*
    * 게시글 삭제하기
    * @param id
    * */
    @Transactional
    // DLETE JPA API 메서드를 활용(영속성 컨텍스트), JPQL --> QDSL .. namedQuery. ...
    public void deleteById(Integer id) {
        Query jpql = em.createQuery("delete from board_tb b where b.id = :id");
        jpql.setParameter("id", id);
        jpql.executeUpdate();
    }

    /**
     *
     *  JPA API 만들어 보기
     */
    // DELETE JPA API 메서드를 활용(영속성 컨텍트), JPQL --> QDSL .. namedQuery. ...
    @Transactional // 트랜잭션 내에서 실행되도록 보장
    public void deleteByIdJPA(int id) {

    }

    /*
    * 게시글 업데이트
    * 두 가지 방식으로 연습 - JQPL 사용, JPA API
    * */
    @Transactional
    public void updateV1(int id, String title, String content) {
        Query jpql = em.createQuery("update board_tb b set b.title = :title, b.content = :content where b.id = :id");
        jpql.setParameter("title", title);
        jpql.setParameter("content", content);
        jpql.setParameter("id", id);
        jpql.executeUpdate();
    }

    @Transactional
    public void updateV2(int id, String title, String content) {
        String jpql = "update board_tb b set b.title = :title, b.content = :content where b.id = :id";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);
        query.setParameter("title", title);
        query.setParameter("content", content);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Transactional
    public void updateV3(int id, String title, String content) {
        TypedQuery<Board> jpql;
        jpql = em.createQuery("update board_tb b set b.title = :title, b.content = :content where b.id = :id", Board.class);
        jpql.setParameter("title", title);
        jpql.setParameter("content", content);
        jpql.setParameter("id", id);
        jpql.executeUpdate();
    }

    @Transactional
    public void updateV4(int id, String title, String content) {
        TypedQuery<Board> jpql = em.createQuery("update board_tb b set b.title = :title, b.content = :content where b.id = :id", Board.class);
        jpql.setParameter("title", title);
        jpql.setParameter("content", content);
        jpql.setParameter("id", id);
        jpql.executeUpdate();
    }

    @Transactional
    public void updateByIdJPA(int id, String title, String content) {
        Board board = em.find(Board.class, id);
        if (board != null) {
            board.setTitle(title);
            board.setContent(content);
        }
        // flush 명령, commit 명령 할 필요 없이
        // jqpl 방식은 대용량 레코드 수정 방식에 적합하다~ 반대로 JPA API 방식은 단일 레코드 수정에 유리하다~
        // jqpl은 database에 직접 실행~ 반대로 JPA API는 영속성 컨텍스트의 객체에 직접 수정~
        // 트랜잭션을 선언하면 ---> 더티 체킹
        // 더티 쳌(I'm Korean...)킹 이란?
        //
    }
}
