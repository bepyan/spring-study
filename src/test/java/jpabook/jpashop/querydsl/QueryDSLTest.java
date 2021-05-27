package jpabook.jpashop.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.QMember;
import jpabook.jpashop.domain.QOrder;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.QItem;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.query.JpaQueryCreator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.IntStream;

// 더미 데이터
// memberA gumi gumi 39177
// memberB gumi gumi 39177

// item1    100000  5
// item2    5000    90
// item3    500     50

// memberA  item1   100000  5   ORDER
// memberB  item2   5000  5   ORDER
// memberA  item2   5000  5   ORDER

@SpringBootTest
@Transactional
public class QueryDSLTest {
    @PersistenceContext
    private EntityManager em;

    @Test
    public void 기본() throws Exception{
        JPAQueryFactory query = new JPAQueryFactory(em);
        QMember member = QMember.member;

        List<Member> members = query.select(member) // selectFrom(member)로 줄일 수도 있다.
                .from(member)
                .where(member.name.contains("member"))
                .orderBy(member.name.desc())
                .fetch();
        members.stream().forEach(a -> System.out.println(a.getName()));
    }

    @Test
    public void 조건() throws Exception{
        JPAQueryFactory query = new JPAQueryFactory(em);
        QItem item = QItem.item;

        List<Item> items = query.select(item)
                .from(item)
                .where(item.name.contains("item").and(item.price.gt(3000)).or(item.stockQuantity.eq(50))) // chainning 방식
//                .where(item.name.contains("item"), item.price.gt(3000), item.stockQuantity.eq(50)) // ,(파라미터)는 and 연산으로 된다.
                .fetch();
        items.stream().forEach(a -> System.out.println("a.getName() = " + a.getName()));
    }

    @Test
    public void 정렬() throws Exception{
        JPAQueryFactory query = new JPAQueryFactory(em);
        QItem item = QItem.item;
        List<Item> members = query.select(item)
                .from(item)
                .orderBy(item.price.asc(), item.stockQuantity.asc().nullsLast()) // 오름차순, 조건이 같은데 다른 값이 null이면 마지막으로 정렬된다. // 시험에 나오기 좋다구???
                .fetch();
        // 예제 https://blog.naver.com/PostView.nhn?blogId=adamdoha&logNo=222348861846&redirect=Dlog&widgetTypeCall=true&directAccess=false

        members.stream().forEach(a -> System.out.println("a.getName() = " + a.getName()));
    }

    @Test
    public void 동적쿼리() throws Exception{
        JPAQueryFactory query = new JPAQueryFactory(em);
        QItem item = QItem.item;
        SearchParams params = new SearchParams();
        params.setName("item");
        params.setPrice(5000);

        BooleanBuilder builder = new BooleanBuilder();
        // 빈 텍스트인지 null인지 확인
//        if(StringUtils.hasText(params.getName()))
//            builder.and(item.name.contains(params.getName()));
//
//        if(params.getPrice() != null)
//            builder.and(item.price.gt(params.getPrice()));

        List<Item> items = query.selectFrom(item)
//                .where(builder)
                .where(itemNameContain(params), itemPriceGt(params))
                .fetch();

        items.stream().forEach(a -> System.out.println("a.getName() = " + a.getName()));
    }

    // builder에서 이렇게 분리해주는게 유지보수에 좋다. 재사용성.
    private BooleanExpression itemNameContain(SearchParams params){
        if(!StringUtils.hasText(params.getName()))
            return null;
        return QItem.item.name.contains(params.getName());
    }
    private BooleanExpression itemPriceGt(SearchParams params){
        if(params.getPrice() == null)
            return null;
        return QItem.item.price.gt(params.getPrice());
    }

    @Test
    public void 페이징() throws Exception{
        JPAQueryFactory query = new JPAQueryFactory(em);
        QItem item = QItem.item;

//        List<Item> items = query.selectFrom(item)
//                .offset(0)
//                .limit(2)
//                .orderBy(item.price.asc())
//                .fetch();

        // 전체 개수를 구하고 싶으면
        QueryResults<Item> results = query.selectFrom(item)
                .offset(0)
                .limit(2)
                .orderBy(item.price.asc())
                .fetchResults();
        System.out.println("results.getTotal() = " + results.getTotal());

        List<Item> items = results.getResults();
        items.stream().forEach(a -> System.out.println("a.getName() = " + a.getName()));
    }

    @Test
    public void 그룹() throws Exception{
        JPAQueryFactory query = new JPAQueryFactory(em);
        QItem item = QItem.item;
        List<Tuple> counts = query.selectFrom(item)
                .groupBy(item.price)
                .select(item.price, item.price.count())
                .fetch();
        IntStream
                .range(0, counts.size())
                .mapToObj(idx -> String.format("%d -> %s",
                        counts.get(idx).toArray()[0],
                        counts.get(idx).toArray()[1]))
                .forEach(System.out::println);
    }

    // *** 엔티티를 그대로 가져오는 것이 아닌
    @Test
    public void 전용DTO() throws Exception{
        JPAQueryFactory query = new JPAQueryFactory(em);
        QMember member = QMember.member;
        List<MemberDTO> memberDTOs = query.select(Projections.bean(MemberDTO.class,
                    member.id,
                    member.name.as("username"))
                )
                .from(member)
                .fetch();
        memberDTOs.stream().forEach(dto -> System.out.println("dto.getUsername() = " + dto.getUsername()));
    }

    @Test
    public void 다대일조인() throws Exception{
        JPAQueryFactory query = new JPAQueryFactory(em);
        QMember member = QMember.member;
        QOrder order = QOrder.order;

//        List<Order> orderWithMember = query.selectFrom(order)
//                .join(order.member, member)
//                .fetch();
//        orderWithMember.stream().forEach(o -> System.out.println("o.getId() = " + o.getMember().getName()));
        // 첫 select은 join에 대한 결과를 가져온다.
        // 이후 각 맴버의 name을 가져오는 select를 가져온다. (lazy로딩이기 때문에)
        // N+1 문제 이다.

        // lazy를 Eager로 변경하는 것보다 패치 조인을 통해 해결해야 한다.
        List<Order> orderWithMemberFetchJoin = query.selectFrom(order)
                .join(order.member, member)
                .fetchJoin()
                .fetch();
        orderWithMemberFetchJoin.stream().forEach(o -> System.out.println("o.getId() = " + o.getMember().getName()));
        orderWithMemberFetchJoin.stream().forEach(o -> System.out.println("o.getMember().getName() = " + o.getOrderItems().get(0).getItem().getName()));

        // JPQL
        // 쿼리 중간 중간 띄어쓰기 주의..
//        List<Order> orderWithMemberJPQL = em.createQuery(
//    "select o from Order o" +
//            " join fetch o.member m" +
//            " join fetch o.orderItems oi" +
//            " join fetch oi.item i", Order.class
//        ).getResultList();
//        orderWithMemberJPQL.stream().forEach(o -> System.out.println("o.getId() = " + o.getMember().getName()));
    }
    
    @Test
    public void 일대다조인() throws Exception{
        JPQLQueryFactory query = new JPAQueryFactory(em);
        QMember member = QMember.member;
        QOrder order = QOrder.order;

        // 페이징이 불가능하다. Order에도 Member 데이터가 중복으로 존재하기 때문.
        // 다대일은 상관이 없다.
        // distinct하면 결과에 중복이 없어지지만 쿼리는 innerJoin이 발생해서 중복된다.
        // ** 모든 조인 결과를 일단 메모리에 올리고 메모리에서 페이징 수행하는 경고가 발생..

//        List<Member> resultList = em.createQuery("select distinct m from Member m" +
//                " join fetch m.orders o", Member.class)
//                // JPQL에서의 페이징
////                .setFirstResult(0)
////                .setMaxResults(2)
//                .getResultList();
//        resultList.stream().forEach(m -> System.out.println("m.getName() = " + m.getName()));

        // 대상 엔티티쿼리를 조인없이 가져오고 패치조인이 아닌 batch size를 통해 해결한다.
        List<Member> resultList = em.createQuery("select distinct m from Member m", Member.class)
                .setFirstResult(0)
                .setMaxResults(2)
                .getResultList();
        resultList.stream().forEach(m -> m.getOrders().stream().forEach(o -> System.out.println("o.getOrderDate() = " + o.getOrderDate())));


    }
}
