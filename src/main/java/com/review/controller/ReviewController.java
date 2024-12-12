package com.review.controller;

import com.review.dto.SliceResponseDto;
import com.review.dto.ReviewRequestDto;
import com.review.dto.ReviewResponseDto;
import com.review.service.ReviewService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/{productId}/reviews", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ReviewResponseDto createReview(@PathVariable Long productId, @RequestPart(value = "reviewRequestDto")ReviewRequestDto reviewRequestDto,
                                          @RequestPart(value = "image", required = false) MultipartFile image) {
        return reviewService.createReview(productId,reviewRequestDto,image);
    }
}
