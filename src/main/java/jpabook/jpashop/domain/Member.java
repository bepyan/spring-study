package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NamedQuery(
        name="Member.findByName",
        query = "select m from Member m where name = :name"
)
@NamedQueries(
        {
                @NamedQuery(
                        name = "Member.findByName",
                        query = "select m from Member m where m.teamId = :teamId"
                ),
                @NamedQuery(
                        name = "Member.findByTeamId",
                        query = "select m from Member m where m.teamId = :teamId"
                )
        }
)

public class Member {

    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    private String name;

    @Embedded
    private Address address;
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
