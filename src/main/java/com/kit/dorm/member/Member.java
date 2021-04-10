package com.kit.dorm.member;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Member {
    private Long id;
    private String name;
    private int grade;

    public Member(Long id, String name, int grade) {
        if(grade>4 || grade<1){
            throw new IllegalArgumentException("학년은 1이상 4이하여야 함");
        }
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
