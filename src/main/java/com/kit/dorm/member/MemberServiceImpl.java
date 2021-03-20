package com.kit.dorm.member;

public class MemberServiceImpl implements MemberService {
    /* FileMemberStorage가 아닌 interface를 가져오는게 국룰 */
    // private final MemberStorage memberStorage = new DbMemberStorage();
    // private final MemberStorage memberStorage = new FileMemberStorage();
    /* 코드 수정 사항이 불가피한 단점이 존재(OCP 준수 X) => AppConfig로 해결(의존성 주입) */
    private MemberStorage memberStorage;

    public MemberServiceImpl(MemberStorage memberStorage) {
        this.memberStorage = memberStorage;
    }

    @Override
    public void register(Member member) {
        memberStorage.store(member);
    }
    @Override
    public Member findMember(Member member) {
        return memberStorage.findById(member.getId());
    }
}
