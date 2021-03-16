package com.kit.dorm;

import com.kit.dorm.fee.BookService;
import com.kit.dorm.fee.BookServiceImpl;
import com.kit.dorm.fee.OldFeePolicy;
import com.kit.dorm.member.FileMemberStorage;
import com.kit.dorm.member.MemberService;
import com.kit.dorm.member.MemberServiceImpl;

public class AppConfig {
    MemberService memberService(){
        return new MemberServiceImpl(new FileMemberStorage());
    }
    BookService bookService(){
        return new BookServiceImpl(new OldFeePolicy());
    }
}
