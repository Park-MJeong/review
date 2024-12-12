package com.review.controller;

import com.review.dto.SliceResponseDto;
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

//
    @GetMapping("/{productId}/reviews")
    public SliceResponseDto getReviews(@PathVariable Long productId, @RequestParam Long cursor, @RequestParam(defaultValue = "10")int size){
        return reviewService.pagingReviews(productId,cursor,size);
    }

    @PostMapping("/{productId}/reviews")
    public ReviewResponseDto createReview(@PathVariable Long productId,@RequestBody ReviewRequestDto reviewRequestDto) {
        return reviewService.createReview(productId,reviewRequestDto);
    }
}
