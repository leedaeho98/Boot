package com.shoping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart; // 하나의 장바구니에 여러개의 상품을 담아야하므로 다대일 매핑

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; // 상품의 정보를 알아야하므로 매핑

    private int count; // 같은 상품을 장바구니에 몇 개 담을지 저장
}
