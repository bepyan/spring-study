package com.kit.dorm.fee;

import com.kit.dorm.book.DormName;
import com.kit.dorm.member.Member;

public class NewFeePolicy implements FeePolicy{
    private int fee = 0;
    private int addFee = 10000;
    @Override
    public int fee(Member member, DormName dormName) {
        switch (dormName) {
            case PUREUM:
                fee = 1000 + addFee;
                break;
            case OREUM:
                fee = 2000 + addFee;
                break;
            default:
                fee = 0;
        }
        return fee;
    }
}
