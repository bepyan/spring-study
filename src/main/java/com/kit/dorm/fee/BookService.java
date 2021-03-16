package com.kit.dorm.fee;

import com.kit.dorm.book.Book;
import com.kit.dorm.book.DormName;
import com.kit.dorm.member.Member;

public interface BookService {
    Book assignRoom(Member member, DormName dormName, String roomNumber);
}
