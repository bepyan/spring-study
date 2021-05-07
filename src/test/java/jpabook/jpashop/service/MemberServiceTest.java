package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

        @Autowired
        MemberRepository memberRepository;
        @Autowired MemberService memberService;

        @Test
//        @Rollback(false)  // 하면 insert 쿼리가 보일 것이다.
        public void 화원가입()throws Exception{
            Member member = new Member();
            member.setName("kim");
            // insert 쿼리(persist)가 나가지 않는다
            // 테스트에서 @Transactional은 컷밋 되지않고 롤벡한다.
            // 중복 유저 검사하면서 jpql은 실행즉시 쿼리를 실행된다. (영속성 컨택스트 X)
            Long savedId = memberService.join(member);
            // 먼저 영속성 컨텍스트를 뒤지기에 DB를 조회할 필요가 없다.
            // IDENTITY 전략을 사용하면 DB에 저장해야지 ID를 가져올 수 있기에 insert 쿼리가 발생한다.
            Assertions.assertThat(member).isEqualTo(memberService.findOne(savedId));
        }

        @Test
        public void 중복_회원_예외() throws Exception{
            Member member1 = new Member();
            member1.setName("kim");

            Member member2 = new Member();
            member2.setName("kim");

            memberService.join(member1);

//            IllegalStateException exception = assertThrows(IllegalStateException.class,
//                    ()->memberService.join(member2));
//            String message = exception.getMessage();
//            System.out.println("message = " + message);

            //AssertJ
            assertThatThrownBy(()-> memberService.join(member2))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("이미 존재하는 회원입니다"); // 내가 원하는 메시지까지 알고 싶다. (정확히 같아야한다)
        }
}