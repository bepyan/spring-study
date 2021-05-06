package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "ORDERS") // db에 Order테이블이 있기에 겹치면 안대!
public class Order {
    @Id @GeneratedValue
    @Column(name="ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 결국 모두 LAZY를 사용하자.
    @JoinColumn(name="MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // CSS
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="DELIVERY_ID")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //ORDER, CANCELED

    // 연관관계 편의 메소드
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
