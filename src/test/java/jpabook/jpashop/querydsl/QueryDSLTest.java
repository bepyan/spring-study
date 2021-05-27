package jpabook.jpashop.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.QMember;
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
}
