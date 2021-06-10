package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaMemberRepository implements MemberRepository{

    @PersistenceContext
    EntityManager em;


    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }

    @Override
    public List<Member> findByName(String name) {
        // JPQL(m.name=:name)은 바로 SQL 실행
//        return em.createQuery("SELECT m FROM Member m WHERE m.name=:name", Member.class)
//                .setParameter("name", name)
//                .getResultList();
        return em.createNamedQuery("Member.findByName", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
