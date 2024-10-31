package com.sparta.memo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
//JPA entity 클래스가 해당 추상 클래스를 상속할 경우 추상클래스에 선언한 멤버변수를 컬럼으로 인식
//자바 객체지향의 상속과 같은 개념
//abstract 클래스로 선언한 이유는 해당 클래스 자체를 객체로 생성할 일이 없어서 abstract 로 선언한 것.
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

    @CreatedDate
    //자동으로 생성된 시간 저장
    @Column(updatable = false)
    //업데이트 안되게 처리
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    //데이터 변경 시간 저장
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}