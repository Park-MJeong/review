package com.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto { //리뷰요청보냄 ,post
    private Long userId;
    private int score;
    private String content;


}
