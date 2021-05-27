package jpabook.jpashop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Order order){
        em.persist(order);
    }
    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    //  JPQL 쿼리 빌더 QueryDSL
    public List<Order> findAll(OrderSearch orderSearch){
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .select(order)
                .from(order)
                .join(order.member, member)
                .where(statusEq(orderSearch), nameLike(orderSearch))
                .limit(1000)
                .fetch();
    }
    // 재사용성을 위해 분리.
    private BooleanExpression nameLike(OrderSearch orderSearch){
        if(!StringUtils.hasText(orderSearch.getMemberName()))
            return null;
        return QMember.member.name.like(orderSearch.getMemberName());
    }
    private BooleanExpression statusEq(OrderSearch orderSearch){
        if(orderSearch.getOrderStatus() == null)
            return null;
        return QOrder.order.status.eq(orderSearch.getOrderStatus());
    }

//    // JPQL은 불편한다.. => 위의 QueryDSL을 사용하자
//    public List<Order> findAll(OrderSearch orderSearch){
//        String jpql = "SELECT o FROM Order o JOIN o.member m";
//        boolean isFirstCondition = true;
//
//        //주문 상태 검색
//        if (orderSearch.getOrderStatus() != null) {
//            if (isFirstCondition) {
//                jpql += " where";
//                isFirstCondition = false;
//            } else
//                jpql += " and";
//            jpql += " o.status = :status";
//        }
//
//        //회원 이름 검색
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            if (isFirstCondition) {
//                jpql += " where";
//                isFirstCondition = false;
//            } else
//                jpql += " and";
//            jpql += " m.name like :name";
//        }
//        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000); //최대 1000건
//        if (orderSearch.getOrderStatus() != null)
//            query = query.setParameter("status", orderSearch.getOrderStatus());
//
//        if (StringUtils.hasText(orderSearch.getMemberName()))
//            query = query.setParameter("name", orderSearch.getMemberName());
//
//        return query.getResultList();
//    }
}
