package com.kit.dorm.member;

import java.util.HashMap;
import java.util.Map;

public class DbMemberStorage implements MemberStorage {
    private static Map<Long,Member> members = new HashMap<>();

    @Override
    public void store(Member member) {
        System.out.println("==========to db storagy==========");
        members.put(member.getId(),member);
    }

    @Override
    public Member findById(Long memberId) {
        System.out.println("==========from db storagy==========");
        return members.get(memberId);
    }
}
