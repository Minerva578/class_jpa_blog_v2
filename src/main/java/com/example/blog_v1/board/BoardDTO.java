package com.example.blog_v1.board;

import com.example.blog_v1.user.User;
import lombok.Data;

public class BoardDTO {
    // 흐름 - 클라이언트 post 요청 -> 요청 파라미터 SaveDTO 객체변환 및 저장 -> 컨트롤러
    @Data
    public static class SaveDTO {
        private String title;
        private String content;

        public Board toEntity(User user) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
        }
    }

}
