package com.kit.dorm.fee;

import com.kit.dorm.book.DormName;
import com.kit.dorm.member.Member;

import static com.kit.dorm.book.DormName.*;

public class OldFeePolicy implements FeePolicy{
    private int fee=0;
    @Override
    public int fee(Member member, DormName dormName) {
        switch (dormName) {
            case PUREUM:
                fee = 1000;
                break;
            case OREUM:
                fee = 2000;
                break;
            default:
                fee = 500;
        }
        return fee;
    }
}
