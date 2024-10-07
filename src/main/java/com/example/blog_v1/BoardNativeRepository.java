package com.example.blog_v1;

import com.example.blog_v1.board.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository // IoC
public class BoardNativeRepository {
    // DI 처리
    private final EntityManager em;
    /*
    *  새로운 게시를 생성
    * @param title
    * @param content
    * */
    @Transactional
    public void save(String title, String content) {
        Query query = em.createNativeQuery(
                "insert into board_tb(title, content, created_at) values(?, ?, now())"
        );

        query.setParameter(1, title);
        query.setParameter(2, content);

        // 실행
        query.executeUpdate();
    }

    /*
    * 특정 ID 게시글을 조회 합니다.
    * @param id
    * @return
    * */
    public Board findById(int id) {
        Query query = em.createNativeQuery(
                "select * from board_tb where id = ?", Board.class
        );
        query.setParameter(1, id);
        return (Board) query.getSingleResult();
    }

    /*
    * 모든 게시글 조회
    * @return
    * */
    public List<Board> findAll() {
        Query query = em.createNativeQuery(
                "select * from board_tb order by id desc", Board.class);
        return query.getResultList();
    }

    /*
    * 특정 ID로 게시글을 수정하는 기능
    * @param id
    * @param title
    * @param content
    * */
    public void update(int id, String title, String content) {
        Query query = em.createNativeQuery(
                "update board_tb set tile = ?, content = ? where id = ?"
        );
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, id);

        query.executeUpdate();
    }

    /*
    * 특정 ID의 게시글을 삭제 합니다.
    * @param id
    * */
    @Transactional
    public void delete(int id) {
       Query query = em.createNativeQuery("delete from board_tb where id = ?");
       query.setParameter(1, id);

       query.executeUpdate();
    }
}
