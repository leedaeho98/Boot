package com.shoping.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 이 클래스는 상품 데이터 출력용
@Getter @Setter // 데이터를 주고 받는다
public class ItemDto {

    private Long id;

    private String itemNm;

    private Integer price;

    private String itemDetail;

    private String sellStatCd;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

}
