package com.review.dto;

import lombok.Getter;

@Getter
public class ReviewRequestDto { //리뷰요청보냄 ,post
    private Long userId;
    private int score;
    private String content;
}
