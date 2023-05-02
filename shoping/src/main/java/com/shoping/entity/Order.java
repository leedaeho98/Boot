package com.shoping.entity;

import com.shoping.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 주문엔티티 클래스
@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 한 명의 회원은 여러 번 주문해야하므로 다대일 단방향 매핑

    private LocalDateTime orderDate; // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문상태

    // 연관관계의 주인인 필드인 order를 mappedBy의 값으로 세팅
    // 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 전이하는 CascadeTypeAll
    // 고아 객체 제거를 사용하기 위해서 orphanRemoval =true
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>(); // 하나의 주문이 여러개의 주문상품을 갖으므로 List로 매핑

//    private LocalDateTime regTime; // 현재시간
//
//    private LocalDateTime updateTime;
}
