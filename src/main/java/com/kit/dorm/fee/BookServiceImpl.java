package com.kit.dorm.fee;

import com.kit.dorm.book.Book;
import com.kit.dorm.book.DormName;
import com.kit.dorm.member.Member;

public class BookServiceImpl implements BookService{
    // private final FeePolicy feePolicy = new OldFeePolicy();
    private final FeePolicy feePolicy;

    public BookServiceImpl(FeePolicy feePolicy) {
        this.feePolicy = feePolicy;
    }

    @Override
    public Book assignRoom(Member member, DormName dormName, String roomNumber) {
        int fee = feePolicy.fee(member, dormName);
        return new Book(member, dormName, fee);
    }
}
