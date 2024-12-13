package com.review.service;

import com.review.dto.SliceResponseDto;
import com.review.dto.ReviewRequestDto;
import com.review.dto.ReviewResponseDto;
import com.review.entity.Product;
import com.review.entity.Review;
import com.review.repository.ProductRepository;
import com.review.repository.ReviewRepository;
import com.review.util.ImageDummy;
import com.review.util.ReviewSlice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ImageDummy imageDummy;

    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository, ImageDummy imageDummy) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.imageDummy = imageDummy;
    }

    //    리뷰 작성
    @Transactional
    public ReviewResponseDto createReview(Long productId, ReviewRequestDto reviewRequestDto, MultipartFile image) {
        Product product = findProduct(productId);

        String imageUrl=null;
        if(image!=null && !image.isEmpty()) imageUrl=imageDummy.upload(image);

        if(!reviewRepository.existsByProductIdAndUserId(productId,reviewRequestDto.getUserId())){
                Review review = new Review(reviewRequestDto);
                review.setImageUrl(imageUrl);
                Review saveReview =  reviewRepository.save(review);
                saveReview.setProduct(product);
                ReviewResponseDto reviewResponseDto = new ReviewResponseDto(saveReview);

                updateProductInfo(productId,product);

                return reviewResponseDto;
            }else throw new IllegalArgumentException("해당 제품에 이미 리뷰를 작성한 유저입니다.");
    }
// 리뷰작성시 제품정보 업데이트
    public void updateProductInfo(Long productId,Product product){
        product.setReviewCount(reviewRepository.countByProductId(productId));
        product.setScore(reviewRepository.findAverageByProductId(productId));
    }

    public SliceResponseDto<ReviewResponseDto> pagingReviews(Long productId, Long cursor, int size){
        Product product = findProduct(productId); //해당 제품의 정보 가져옴 (전체리뷰수,평균별점)
        Slice<ReviewResponseDto> reviews =getReviews(productId,cursor,size);

        return new SliceResponseDto<>(product,cursor,reviews);
    }

    public Slice<ReviewResponseDto> getReviews(Long productId,Long cursor,int size) {
        Slice<Review> reviews;
        int limitSize = ReviewSlice.sliceLimit(size);
        if(cursor==null){
            reviews =reviewRepository.findByProductIdOrderByCreatedAtDesc(productId, Pageable.ofSize(limitSize));
        }else {
            reviews = reviewRepository.findByProductIdLessThanOrderByCreatedAtDesc(productId,cursor,Pageable.ofSize(limitSize));
        }
        List<ReviewResponseDto> reviewList =reviews.getContent()
                                                    .stream()
                                                    .map(ReviewResponseDto::new)
                                                    .collect(Collectors.toList());

        return ReviewSlice.getSlice(reviewList,size);

    }

    private Product findProduct(Long id) {
        return productRepository.findById(id).
                orElseThrow(()->new IllegalArgumentException("해당 제품은 존재하지 않는 제품입니다."));
    }

}
