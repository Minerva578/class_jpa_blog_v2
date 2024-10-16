-- 사용자 데이터 삽입
INSERT INTO user_tb (username, password, email, role, created_at) VALUES
('길동', '1234', 'a@nate.com', 'USER', NOW()),
('둘리', '1234', 'b@nate.com', 'USER', NOW()),
('마이콜', '1234', 'c@nate.com', 'ADMIN', NOW());

-- 게시글 데이터 삽입
INSERT INTO board_tb (title, content, user_id, created_at) VALUES
('제목1', '내용1', 1, NOW()),
('제목2', '내용2', 1, NOW()),
('제목3', '내용3', 2, NOW()),
('제목4', '내용4', 3, NOW());


-- 댓글 데이터 삽입
INSERT INTO reply_tb (comment, user_id, board_id, status, created_at) VALUES
('댓글1', 1, 4, 'DELETED', NOW()),
('댓글1', 1, 4, 'ACTIVE', NOW()),
('댓글2', 1, 4, 'DELETED', NOW()),
('댓글3', 2, 4, 'ACTIVE', NOW()),
('댓글4', 2, 3, 'ACTIVE', NOW());

