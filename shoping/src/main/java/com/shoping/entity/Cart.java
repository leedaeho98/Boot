package com.shoping.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart {

    @Id // 기본키 지정
    @Column(name = "cart_id") // 컬럼명 지정
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // 일대일 매핑
    @JoinColumn(name="member_id")
    private Member member;


}
