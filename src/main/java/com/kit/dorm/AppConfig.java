package com.kit.dorm;

import com.kit.dorm.fee.*;
import com.kit.dorm.member.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    MemberService memberService(){
        return new MemberServiceImpl(memberStorage());
    }
    @Bean
    BookService bookService(){
        return new BookServiceImpl(feePolicy());
    }
    @Bean
    MemberStorage memberStorage(){
//        return new DbMemberStorage();
        return new FileMemberStorage();
    }
    @Bean
    FeePolicy feePolicy(){
        return new NewFeePolicy();
    }
}
