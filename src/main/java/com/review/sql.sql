CREATE TABLE IF NOT EXISTS `product` (
                                         `id`          BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                         `review_count` BIGINT(20) NOT NULL,
                                         `score`       FLOAT  NOT NULL
) ENGINE = InnoDB CHARSET = utf8;

# Product 더미 데이터 1번 상품 생성
INSERT INTO Product (id, review_count, score)
VALUES (15, 0, 0.0);

select * from review where product_id=2 and id<9 order by created_at desc ;

INSERT INTO review (review.product_id, review.user_id, score, content, image_url, created_at) VALUES
                                                                                                  (2, 1, 5, '이걸 사용하고 제 인생이 달라졌습니다.', '/image.png', '2024-11-25 00:00:00'),
                                                                                                  (3, 3, 5, '이걸 사용하고 제 인생이 달라졌습니다.', NULL, '2024-11-24 00:00:00'),
                                                                                                  (2, 2, 4, '만족합니다.', NULL, '2024-11-23 00:00:00'),
                                                                                                  (3, 4, 3, '보통입니다.', NULL, '2024-11-22 00:00:00'),
                                                                                                  (2, 5, 2, '별로입니다.', NULL, '2024-11-21 00:00:00'),
                                                                                                  (2, 6, 1, '실망입니다.', NULL, '2024-11-20 00:00:00'),
                                                                                                  (2, 7, 4, '괜찮습니다.', NULL, '2024-11-19 00:00:00'),
                                                                                                  (3, 8, 3, '보통입니다.', NULL, '2024-11-18 00:00:00'),
                                                                                                  (4, 9, 5, '이걸 사용하고 제 인생이 달라졌습니다.', '/image2.png', '2024-11-17 00:00:00'),
                                                                                                  (5, 10, 4, '만족합니다.', NULL, '2024-11-16 00:00:00'),
                                                                                                  (4, 11, 3, '보통입니다.', NULL, '2024-11-15 00:00:00'),
                                                                                                  (3, 13, 1, '실망입니다.', NULL, '2024-11-13 00:00:00'),
                                                                                                  (6, 14, 4, '괜찮습니다.', NULL, '2024-11-12 00:00:00'),
                                                                                                  (8, 15, 3, '보통입니다.', NULL, '2024-11-11 00:00:00');
UPDATE product p
SET
    review_count = (SELECT COUNT(*) FROM review r WHERE r.product_id = p.id),
    score = (SELECT AVG(r.score) FROM review r WHERE r.product_id = p.id)
WHERE p.id IN (SELECT DISTINCT r.product_id FROM review r);

SELECT
    CONCAT(ROUND(COUNT(DISTINCT id) / COUNT(*) * 100, 2), '%') AS id_cardinality,
    CONCAT(ROUND(COUNT(DISTINCT product_id) / COUNT(*) * 100, 2), '%') AS product_id_cardinality,
    CONCAT(ROUND(COUNT(DISTINCT content) / COUNT(*) * 100, 2), '%') AS content_cardinality,
    CONCAT(ROUND(COUNT(DISTINCT user_id) / COUNT(*) * 100, 2), '%') AS user_id_cardinality,
    CONCAT(ROUND(COUNT(DISTINCT created_at) / COUNT(*) * 100, 2), '%') AS created_at_cardinality,
    CONCAT(ROUND(COUNT(DISTINCT score) / COUNT(*) * 100, 2), '%') AS score_cardinality
FROM review;

#인덱스 설정
# 전체 데이터를 스캔하지않고 인덱스를 활용해 필요한 데이터만 조회
# 조회속도 향상,대규모데이터에서 성능 이슈를 줄여줌
create index idx_review_product_id_created_at on review(product_id,created_at desc);

EXPLAIN SELECT *
        FROM review
        WHERE product_id = 3
        ORDER BY created_at DESC;
