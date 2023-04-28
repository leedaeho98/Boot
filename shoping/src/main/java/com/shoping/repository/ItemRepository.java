package com.shoping.repository;

import com.shoping.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>,QuerydslPredicateExecutor<Item> { // <테이블명,기본키 타입>

    // 파라미터로 넘어온 상품명(itemNm) 또는 상품 상세설명(itemDetail) 값이 같으면 출력
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    // 파라미터로 넘어온 price의 값보다 작은 상품부터 데이터를 조회
    List<Item> findByPriceLessThan(Integer price);

    // 파라미터로 넘어온 price의 값보다 작을경우 높은순부터 출력
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    @Query(value ="select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);


}
