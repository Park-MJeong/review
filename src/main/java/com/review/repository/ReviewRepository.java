package com.review.repository;

import com.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean  existsByProductIdAndUserId(Long productId,Long id); //해당 아이디로 해당 제품에 리뷰를 쓴 적 있는지
    Long countByProductId(Long id); //해당제품의 리뷰 숫자

    @Query("select AVG(r.score)from Review r where r.product.id=:productId")
    Float findAverageByProductId(@Param("productId") Long id); //해당제품의 별점
}
