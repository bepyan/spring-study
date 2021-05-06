package jpabook.jpashop.study.controller;

import java.util.ArrayList;
import java.util.List;

public class MemberRepository {
    private static final List<Member> members = new ArrayList<>();
    private static Long seq = 0L;

    public void save(Member member){
        member.setId(++seq);
        members.add(member);
    }
    public List<Member> findAll(){
        return members;
    }

    public Member findById(Long memberId){
        Member findMember = members.stream()
                .filter(member -> memberId == member.getId())
                .findAny()
                .orElse(null);
        return findMember;
    }
}
