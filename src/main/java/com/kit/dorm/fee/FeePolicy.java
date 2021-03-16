package com.kit.dorm.fee;

import com.kit.dorm.book.DormName;
import com.kit.dorm.member.Member;

public interface FeePolicy {
    int fee(Member member, DormName dormName);
}
