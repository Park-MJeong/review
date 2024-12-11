package com.review.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name ="Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long reviewCount;
    @Column
    private Float score;

//    제품한개가  여러개의 리뷰를 가짐
    @OneToMany(mappedBy = "product" )
    private List<Review> reviews = new ArrayList<>();

}
