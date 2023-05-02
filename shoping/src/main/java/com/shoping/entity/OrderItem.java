package com.shoping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// 주문엔티티 클래스
@Entity
@Getter
@Setter
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // 한 명의 회원은 여러 번 주문해야하므로 다대일 단방향 매핑

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

//    private LocalDateTime regTime; // 현재시간

//    private LocalDateTime updateTime;
}
