package com.kit.dorm;

import com.kit.dorm.book.Book;
import com.kit.dorm.book.DormName;
import com.kit.dorm.fee.BookService;
import com.kit.dorm.fee.BookServiceImpl;
import com.kit.dorm.member.Member;
import com.kit.dorm.member.MemberService;
import com.kit.dorm.member.MemberServiceImpl;

public class BookApp {
    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl();
//        BookService bookService = new BookServiceImpl();
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        BookService bookService = appConfig.bookService();


        Long memberId=1L;
        Member member = new Member(memberId,"kim",1);
        memberService.register(member);

        Member findMember = memberService.findMember(member);
        Book book = bookService.assignRoom(findMember, DormName.PUREUM,"101");
        System.out.println("book = " + book);
    }
}
