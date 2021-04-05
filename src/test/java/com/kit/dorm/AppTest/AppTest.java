package com.kit.dorm.AppTest;

import com.kit.dorm.ComAppConfig;
import com.kit.dorm.member.Member;
import com.kit.dorm.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComAppConfig.class);

    @Test
    void AOP_테스트(){
        MemberService memberService = ac.getBean(MemberService.class);
        Member memberA = new Member(1L, "kit", 1);
        memberService.register(memberA);
        Member member = memberService.findMember(memberA);
    }
}
