package me.doflamingo.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

  @Id @GeneratedValue
  private Long id;
  private String name;
  @OneToMany(mappedBy = "team",cascade = CascadeType.ALL)
  private List<Member> memberList = new ArrayList<>();

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

  public List<Member> getMemberList() {
    return memberList;
  }

  public void addMember(Member member) {
    this.memberList.add(member);
    member.setTeam(this);

  }
}
