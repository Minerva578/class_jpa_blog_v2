package com.example.blog_v1.user;

import com.example.blog_v1.board.Board;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_tb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true) // 유니크 제약 조건 설정
    private String username;
    private String password;
    private String email;

    // "USER", "ADMIN";
    @Column(nullable = true)
    private String role;

    @CreationTimestamp // 엔티티 생성시 자동으로 현재 시간 입력
    private Timestamp createdAt;

    // 순환참조오류
    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "'}";
    }
}
