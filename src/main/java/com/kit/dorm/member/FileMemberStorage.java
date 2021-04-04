package com.kit.dorm.member;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
@Qualifier("subMemberStoragy")
public class FileMemberStorage implements MemberStorage {
    private static Map<Long,Member> members = new HashMap<>();

    @Override
    public void store(Member member) {
        System.out.println("==========to file storagy==========");
        members.put(member.getId(),member);
    }

    @Override
    public Member findById(Long memberId) {
        System.out.println("==========from file storagy==========");
        return members.get(memberId);
    }
}
