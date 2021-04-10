package com.kit.dorm.member;

import com.kit.dorm.ComAppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleTest {
    @Test
    void simpleTest(){
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComAppConfig.class);
        MemberService memberService = ac.getBean(MemberService.class);
        Member member = new Member(1L,"kim",1);

        //when
        memberService.register(member);
        Member findMember = memberService.findMember(member);

        //then
        System.out.println("member = " + member);
        System.out.println("findMember = " + findMember);

        assertEquals(member,findMember);
    }

    @Test
    void simpleTest1(){
        Member member1 = new Member(1L, "kim", 1);
        Member member2 = new Member(1L, "kim", 2);

        assertEquals(member1,member2,()->member1+"와"+member2+"는 같아야 함");
    }

    @Test
    void throwAssertTest(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Member(1L, "kim", 0));
        String message = exception.getMessage();
        System.out.println("message = " + message);
    }
}
