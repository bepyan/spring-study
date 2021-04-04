package com.kit.dorm.ComponentScan;

import com.kit.dorm.ComAppConfig;
import com.kit.dorm.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ComponentScanTest {
    @Test
    void comScanTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComAppConfig.class);
//        MemberService memberService = ac.getBean(MemberService.class);
        Object memberService = ac.getBean("memberServiceImpl");
        assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
