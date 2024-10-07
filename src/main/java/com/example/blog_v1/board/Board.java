package com.example.blog_v1.board;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity(name = "board_tb")
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 전략 db 위임
    private Integer id;
    private String title;
    private String content;

    // created_at 컬럼과 매핑하며, 이 필드는 데이터 저장시 자동으로 설정 됨
    // 개발자가 넣지 않는다. / 개발자가 수정하지 않는다.
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

}
