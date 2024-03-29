DELIMITER $$

DROP PROCEDURE IF EXISTS insertDummyData$$

CREATE PROCEDURE insertDummyData()
BEGIN
    -- 사용자 데이터 생성
    INSERT INTO users (id, created_at, deleted_at, modified_at, authority, email, image_url,
                       name, oauth_id)
    VALUES (1, NOW(), NULL, NOW(), 'USER', 'user1@example.com', 'https://example.com/image', 'User1', 'oauth_id_1'),
           (2, NOW(), NULL, NOW(), 'USER', 'user2@example.com', 'https://example.com/image', 'User2', 'oauth_id_2');

    -- 게시글 데이터 생성
    INSERT INTO posts (user_id, title, content, created_at, modified_at, deleted_at)
    SELECT
        FLOOR(1 + RAND() * 2) AS user_id,
        CONCAT('Title', num) AS title,
        CONCAT('Content', num) AS content,
        NOW() AS created_at,
        NOW() AS modified_at,
        NULL AS deleted_at
    FROM (SELECT @rownum := @rownum + 1 AS num FROM
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t1,
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t2,
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t3,
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t4,
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t5,
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t6,
               (SELECT @rownum := 0) r
         ) tbl;

    -- 댓글 데이터 생성
    INSERT INTO comments (user_id, content, created_at, modified_at, deleted_at, post_id, parent_id)
    SELECT
        FLOOR(1 + RAND() * 2) AS user_id,
        CONCAT('Comment Content', num) AS content,
        NOW() AS created_at,
        NOW() AS modified_at,
        NULL AS deleted_at,
        FLOOR(1 + RAND() * 10000) AS post_id,
        NULL AS parent_id
    FROM (SELECT @rownum := @rownum + 1 AS num FROM
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t1,
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t2,
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t3,
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t4,
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t5,
               (SELECT @rownum := 0) r
         ) tbl;
    -- 게시글 좋아요 데이터 생성
    INSERT INTO post_like (user_id, like_status, post_id)
    SELECT
        FLOOR(1 + RAND() * 2) AS user_id,
        FLOOR(RAND() * 2) AS like_status,
        FLOOR(1 + RAND() * 10000) AS post_id
    FROM (SELECT @rownum := @rownum + 1 AS num FROM
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t1,
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t2,
               (SELECT @rownum := 0) r
         ) tbl;
    -- 댓글 좋아요 데이터 생성
    INSERT INTO comment_like (user_id, like_status, comment_id)
    SELECT
        FLOOR(1 + RAND() * 2) AS user_id,
        FLOOR(RAND() * 2) AS like_status,
        FLOOR(1 + RAND() * 50000) AS comment_id
    FROM (SELECT @rownum := @rownum + 1 AS num FROM
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t1,
               (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t2,
               (SELECT @rownum := 0) r
         ) tbl;
END$$
DELIMITER ;

CALL insertDummyData();
