package com.kit.dorm;

import com.kit.dorm.member.*;

public class MemberApp {
    public static void main(String[] args) {

//        MemberService memberService = new MemberServiceImpl();
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();

        Member member = new Member(1L,"kim",1);
        memberService.register(member);

        Member findMember = memberService.findMember(member);
        System.out.println("findMember = " + findMember.getName());
        System.out.println("member = " + member.getName());
    }
}
