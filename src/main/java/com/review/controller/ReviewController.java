package com.review.controller;

import com.review.dto.ReviewRequestDto;
import com.review.dto.ReviewResponseDto;
import com.review.service.ReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ReviewController {
    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping("/{productId}/reviews")
    public ReviewResponseDto createReview(@PathVariable Long productId,@RequestBody ReviewRequestDto reviewRequestDto) {
        return reviewService.createReview(productId,reviewRequestDto);
    }
}
