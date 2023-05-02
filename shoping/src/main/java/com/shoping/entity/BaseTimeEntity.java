// 등록자와 수정자를 넣지 않는 테이블을 BaseTimeEntity만 상속받게 해주는 클래스
package com.shoping.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class}) // Auditing을 적용하기 위한 어노테이션
@MappedSuperclass // 부모 클래스를 상속 받는 자식 클래스에 매핑정보 제공
@Getter @Setter
public abstract class BaseTimeEntity {

    @CreatedDate // 엔티티가 생성되어 저장되면 시간 자동저장
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate // 엔티티의 값을 변경할 때 시간 자동저장
    private LocalDateTime updateTime;
}
