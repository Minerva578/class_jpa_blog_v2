package com.example.blog_v1.board;

import com.example.blog_v1.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity(name = "board_tb")
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 전략 db 위임
    private Integer id;
    private String title;
    private String content;

    //@JsonBackReference("board-user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 게시글 작성자 정보

    // created_at 컬럼과 매핑하며, 이 필드는 데이터 저장시 자동으로 설정 됨
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }


}
