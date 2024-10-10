package com.example.blog_v1.user;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDTO {

    // 정적 내부 클래스 모우자
    @Data
    public static class LoginDTO {

        private String username;
        private String password;
    }

    @Data
    public static class JoginDTO {

        private String username;
        private String password;
        private String email;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
        }
    }

    @Data
    public static class UpdateDTO {

        private Integer id;
        private String password;
        private String email;

        public User toEntity() {
            return User.builder()
                    .id(id)
                    .password(password)
                    .email(email)
                    .build();
        }
    }
}
