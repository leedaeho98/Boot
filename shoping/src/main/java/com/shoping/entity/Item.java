package com.shoping.entity;

import com.shoping.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

// Item 테이블
@Entity // 테이블 생성하는 어노테이션
@Table(name="item") // 테이블 이름 어노테이션
@Getter @Setter @ToString

public class Item {

    @Id // 테이블의 기본키 지정
    @Column (name="item_id") // 컬럼명 지정
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 상품 코드

    @Column(nullable = false, length = 50) // 필수입력(NOT NULL) , 문자열크기 50
    private String itemNm; // 상품명

    @Column(name="price", nullable = false) // 컬럼명 price , 필수입력(NOT NULL)
    private int price; // 가격

    @Column(nullable = false) // 필수입력(NOT NULL)
    private int stockNumber; // 재고 수량

    @Lob // 용량이 큰 데이터를 저장할 수 있는 공간
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING) // enum클래스라는것으로 타입은 STRING을 주로 사용한다
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    private LocalDateTime regTime; // 등록 시간

    private LocalDateTime updateTime; // 수정 시간





}
