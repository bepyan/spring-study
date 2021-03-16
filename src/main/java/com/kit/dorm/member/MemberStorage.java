package com.kit.dorm.member;

public interface MemberStorage {
    void store(Member member);
    Member findById(Long memberId);
}

