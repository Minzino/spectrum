DELIMITER $$

DROP PROCEDURE IF EXISTS insertDummyData$$

CREATE PROCEDURE insertDummyData()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE user_id INT;
    DECLARE post_id INT;
    DECLARE comment_id INT;
    DECLARE like_status TINYINT;
    DECLARE parent_id INT;

    -- 사용자 데이터 생성
    WHILE i <= 2
        DO
            INSERT INTO users (id, created_at, deleted_at, modified_at, authority, email, image_url,
                               name, oauth_id)
            VALUES (i, NOW(), NULL, NOW(), 'USER', CONCAT('user', i, '@example.com'),
                    'https://example.com/image', CONCAT('User', i), CONCAT('oauth_id_', i));
            SET i = i + 1;
        END WHILE;

    SET i = 1;

    -- 게시글 데이터 생성
    WHILE i <= 130000
        DO
            SET user_id = FLOOR(1 + RAND() * 2);
            INSERT INTO posts (user_id, title, content, created_at, modified_at, deleted_at)
            VALUES (user_id, CONCAT('Title', i), CONCAT('Content', i), NOW(), NOW(), NULL);
            SET i = i + 1;
        END WHILE;

    SET i = 1;

    -- 댓글 데이터 생성
    WHILE i <= 50000
        DO
            SET user_id = FLOOR(1 + RAND() * 2);
            SET post_id = FLOOR(1 + RAND() * 10000);
            SET parent_id = IF(RAND() < 0.5, NULL, FLOOR(1 + RAND() * 50000));
            IF parent_id IS NOT NULL THEN
                SELECT parent.parent_id
                INTO parent_id
                FROM comments AS parent
                WHERE parent.comment_id = parent_id;
            END IF;
            INSERT INTO comments (user_id, content, created_at, modified_at, deleted_at, post_id,
                                  parent_id)
            VALUES (user_id, CONCAT('Comment Content', i), NOW(), NOW(), NULL, post_id, parent_id);
            SET i = i + 1;
        END WHILE;

    SET i = 1;

    -- 게시글 좋아요 데이터 생성
    WHILE i <= 20000
        DO
            SET user_id = FLOOR(1 + RAND() * 2);
            SET post_id = FLOOR(1 + RAND() * 10000);
            SET like_status = FLOOR(RAND() * 2);
            INSERT INTO post_like (user_id, like_status, post_id)
            VALUES (user_id, like_status, post_id);
            SET i = i + 1;
        END WHILE;

    SET i = 1;

    -- 댓글 좋아요 데이터 생성
    WHILE i <= 10000
        DO
            SET user_id = FLOOR(1 + RAND() * 2);
            SET comment_id = FLOOR(1 + RAND() * 50000);
            SET like_status = FLOOR(RAND() * 2);
            INSERT INTO comment_like (user_id, like_status, comment_id)
            VALUES (user_id, like_status, comment_id);
            SET i = i + 1;
        END WHILE;

END$$
DELIMITER ;

CALL insertDummyData();
