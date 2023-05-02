package com.shoping.entity;

import com.shoping.constant.ItemSellStatus;
import com.shoping.repository.ItemRepository;
import com.shoping.repository.MemberRepository;
import com.shoping.repository.OrderItemRepository;
import com.shoping.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PersistenceContext
    EntityManager em;

    public Item createItem(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){

        Order order = new Order();

        for(int i = 0; i < 3 ; i++){
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem); // 영속성 컨텍스트에 저장되지 않은 orderItem엔티티를 order엔티티에 담음
        }

        orderRepository.saveAndFlush(order); // order엔티티 영속성 컨텍스트에 저장 및 영속성 컨텍스트 객체들 데이터베이스 반영
        em.clear(); // 영속성 컨텍스트 상태 초기화;

        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItems().size());
    }

    // 주문데이터 생성 및 저장
    public Order createOrder(){

        Order order = new Order();

        for(int i = 0; i < 3 ; i++){
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem); // 영속성 컨텍스트에 저장되지 않은 orderItem엔티티를 order엔티티에 담음
        }
        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

    // 주문 엔티티(부모)에서 주문 상품(자식)을 삭제했을 때 orderItem 엔티티가 삭제되는지 테스트
    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest(){
        Order order = this.createOrder();
        order.getOrderItems().remove(0); // order엔티티에서 관리하고 있는 orderItem 0번째 인덱스 제거
        em.flush();
    }

    // orderItem 엔티티만 조회하는 쿼리문
    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest(){
        Order order = this.createOrder(); // 기존에 만들었떤 주문 생성 메소드 order에 저장
        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemId) // order엔티티에 저장햇던 주문상품 아이디를 이용 후 orderitem를 조회
                .orElseThrow(EntityNotFoundException::new);
        System.out.println("Order class" + orderItem.getOrder().getClass()); // orderItem 엔티티에 있는 order 객체의 클래스를 출력
        System.out.println("===========================");
        orderItem.getOrder().getOrderDate();
        System.out.println("===========================");
    }



}