package com.review.entity;

import com.review.dto.ReviewRequestDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name ="review")
public class Review{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long userId; //유저 아이디
    @Column
    private int score; //1~5사이
    @Column
    private String content; //리뷰 내용
    @Column
    private String imageUrl;

    @CreatedDate //jpa Auditing 활성화 필요
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="product_Id")
    private Product product;

    public Review(ReviewRequestDto requestDto){
        this.userId = requestDto.getUserId();
        this.score = requestDto.getScore();
        this.content = requestDto.getContent();
    }

}
