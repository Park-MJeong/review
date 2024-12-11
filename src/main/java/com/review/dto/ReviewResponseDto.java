package com.review.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto { //요청받았을때 표시
    private Long id;
    private Long userId;
    private Long score;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;

}
