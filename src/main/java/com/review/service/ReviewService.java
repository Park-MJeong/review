package com.review.service;

import com.review.dto.ReviewRequestDto;
import com.review.dto.ReviewResponseDto;
import com.review.entity.Product;
import com.review.entity.Review;
import com.review.repository.ProductRepository;
import com.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;

//    리뷰 작성
    @Transactional
    public ReviewResponseDto createReview(Long productId,ReviewRequestDto reviewRequestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException("해당 제품은 존재하지 않는 제품입니다."));

        if(!reviewRepository.existsByProductIdAndUserId(productId,reviewRequestDto.getUserId())){
                Review review = new Review(reviewRequestDto);
                Review saveReview =  reviewRepository.save(review);
                saveReview.setProduct(product);
                ReviewResponseDto reviewResponseDto = new ReviewResponseDto(saveReview);

                updateProductInfo(productId,product);

                return reviewResponseDto;
            }else throw new IllegalArgumentException("해당 제품에 이미 리뷰를 작성한 유저입니다.");
    }

    public void updateProductInfo(Long productId,Product product){
        product.setReviewCount(reviewRepository.countByProductId(productId));
        product.setScore(reviewRepository.findAverageByProductId(productId));
    }
}
