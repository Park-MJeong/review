package com.review.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name ="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long reviewCount;
    @Column
    private Float score;

//    제품한개가  여러개의 리뷰를 가짐
    @OneToMany(mappedBy = "product")
    private List<Review> reviews = new ArrayList<>();

//    테스트:상품 생성자
    public Product(Long id, Long reviewCount, float score) {
        this.reviewCount = reviewCount;
        this.score=score;
    }
}
