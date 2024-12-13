package com.review.Service;

import com.review.dto.ReviewRequestDto;
import com.review.dto.ReviewResponseDto;
import com.review.entity.Product;
import com.review.repository.ProductRepository;
import com.review.repository.ReviewRepository;
import com.review.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest

class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;


    private Product testProduct;

    @BeforeEach
    public void before(){
        testProduct =productRepository.save(new Product(1L,0L,0.0F));

    };
    //    테스트종료후 더미 삭제
    @AfterEach
    public void after(){
        reviewRepository.deleteAll();
        productRepository.deleteAll();}

    @Test
    public void 리뷰작성(){
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(testProduct.getId(),5,"상품이 너무너무 좋아요");
        ReviewResponseDto reviewResponseDto = reviewService.createReview(testProduct.getId(),reviewRequestDto,null);

        assertThat(reviewResponseDto).isNotNull();
        assertThat(reviewResponseDto.getScore()).isEqualTo(5);
        assertThat(reviewResponseDto.getContent()).isEqualTo("상품이 너무너무 좋아요");
        assertThat(reviewResponseDto.getImageUrl()).isNull();
    }
    @Test
    public void 리뷰중복작성(){
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(testProduct.getId(),3,"보통입니다");
        ReviewResponseDto reviewResponseDto = reviewService.createReview(testProduct.getId(),reviewRequestDto,null);

        assertThatThrownBy(() -> reviewService.createReview(testProduct.getId(), reviewRequestDto, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 제품에 이미 리뷰를 작성한 유저입니다.");
    }
    @Test
    @Transactional
    public void 제품정보업데이트(){
        reviewService.createReview(testProduct.getId(), new ReviewRequestDto(2L, 4, "첫 리뷰"), null);
        reviewService.createReview(testProduct.getId(), new ReviewRequestDto(3L, 5, "두 번째 리뷰"), null);


        Product updatedProduct = productRepository.findById(testProduct.getId())
                .orElseThrow(() -> new IllegalArgumentException("제품을 찾을 수 없습니다."));

        assertThat(updatedProduct.getReviewCount()).isEqualTo(2);
        assertThat(updatedProduct.getScore()).isEqualTo(4.5f);
    }
    @Test
    @Transactional
    public void 제품예외처리(){
//reviewService에서 해당메서드는 private되어있으므로 public으로 바꿔서 테스트실행
        assertThatThrownBy(() -> reviewService.findProduct(3L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 제품은 존재하지 않는 제품입니다.");
    }


}