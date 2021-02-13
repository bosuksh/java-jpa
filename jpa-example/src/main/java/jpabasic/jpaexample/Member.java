package jpabasic.jpaexample;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity {

  @Id @GeneratedValue
  private Long id;

  private String username;

  @ManyToOne
  @JoinColumn(name = "TEAM_ID")
  private Team team;

  @OneToOne
  @JoinColumn(name = "LOCKER_ID")
  private Locker locker;

  @OneToMany(mappedBy = "member")
  private List<Order> orderList = new ArrayList<>();


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Locker getLocker() {
    return locker;
  }

  public void setLocker(Locker locker) {
    this.locker = locker;
  }

  public Team getTeam() {
    return team;
  }

  public void updateTeam(Team team) {
    this.team = team;
    this.team.getMemberList().add(this);
  }
}
