package com.review.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class ReviewSlice {
    public static <T>Slice<T> getSlice(List<T> list, int size) {

        boolean hasNext =false; //다음 값여부
        if (list.size() > size) { //list의 크기는 기존값에서 +1한 만큼 최대로 넘어올수 있다.정해준 size보다 크다면 다음 값이 있다는것
            System.out.println(list.size());
            System.out.println(size);
            list.remove(size); //마지막에 +1해서 가져온 값을 삭제해줌
            hasNext = true;
        }
        return new SliceImpl<>(list, Pageable.ofSize(size),hasNext); //Pageable.ofSize(size) 반환할 데이터 크기
    }
    public static int sliceLimit(int size){ //다음페이지가 있는지 확인하기 위해
        return size+1;
    }

}
