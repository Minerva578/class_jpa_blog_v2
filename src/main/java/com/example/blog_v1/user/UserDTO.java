package com.example.blog_v1.user;

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
    }
}
