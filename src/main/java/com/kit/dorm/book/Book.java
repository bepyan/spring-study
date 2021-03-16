package com.kit.dorm.book;

import com.kit.dorm.member.Member;

public class Book {
    private Member member;
    private DormName roomName;
    private int fee;

    public Book(Member member, DormName roomName, int fee) {
        this.member = member;
        this.roomName = roomName;
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Book{" +
                "member=" + member +
                ", roomName=" + roomName +
                ", fee=" + fee +
                '}';
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public DormName getRoomName() {
        return roomName;
    }

    public void setRoomName(DormName roomName) {
        this.roomName = roomName;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
