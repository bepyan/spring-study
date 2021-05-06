package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Getter
@Setter // 실습이니까 열어두자
@Embeddable // 둘 중 하나만 명시해도 무방하다
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
