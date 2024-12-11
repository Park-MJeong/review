package com.review.dto;

import com.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto { //요청받았을때 표시
    private Long id;
    private Long userId;
    private int score;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.userId = review.getUserId();
        this.score=review.getScore();
        this.content=review.getContent();
        this.imageUrl=review.getImageUrl();
        this.createdAt=review.getCreatedAt();
    }
}
