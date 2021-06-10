package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewMemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.name=:name")
    List<Member> findByName(@Param("name")String name );

    Page<Member> findByNameStartingWith(String name, Pageable pageable);




}
