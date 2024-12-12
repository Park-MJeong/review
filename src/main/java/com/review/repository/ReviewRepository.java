package com.review.repository;

import com.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean  existsByProductIdAndUserId(Long productId,Long id); //해당 아이디로 해당 제품에 리뷰를 쓴 적 있는지
    Long countByProductId(Long id); //해당제품의 리뷰 숫자

    @Query("select AVG(r.score)from Review r where r.product.id=:productId")
    Float findAverageByProductId(@Param("productId") Long id); //해당제품의 별점평균

//Slice: content(실제 가져온 데이터정보들,최대 size크기만큼만), hasNext(다음페이지가 있는지 여부)반환
    Slice<Review> findByProductIdOrderByIdDesc(Long productId, Pageable pageable); //cursor가 null일때

    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.id < :cursor ORDER BY r.id DESC")
    Slice<Review> findByProductIdLessThanOrderByIdDesc(@Param("productId") Long id, @Param("cursor") Long cursor,Pageable pageable);
}
