package com.review.dto;

import com.review.entity.Product;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.awt.*;
import java.util.List;

@Getter
public class SliceResponseDto<T>{
    private Long totalCount;
    private Float score;
    private Long cursor;
    private List<T> reviews;

    public SliceResponseDto(Product product,Long cursor, Slice<T> slice) {
        this.totalCount =product.getReviewCount();
        this.score = product.getScore();
        this.cursor = cursor;
        this.reviews = slice.getContent();
    }

}
