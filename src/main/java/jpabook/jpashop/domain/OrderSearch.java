package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {
    // 있을 수도 있고 없을 수도 있고.. 까다롭다 JPQL은 문자열 덧셈 연산(동적 쿼리)을 쓸 수 밖에 없다.
    // 에러날 확율이 높고 유지보수가 좋지않다.
    // => JPQL 쿼리 빌더 API를 사용
    private String memberName;
    private OrderStatus orderStatus;
}
