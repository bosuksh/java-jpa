package jpabasic.jpaexample;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Member extends BaseEntity {

  @Id @GeneratedValue
  private Long id;

  private String username;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "TEAM_ID")
  private Team team;

  @Embedded
  private Period period;

  @Embedded
  private Address homeAddress;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "MEMBER_ID")
  private List<AddressEntity> addressHistory = new ArrayList<>();

  @ElementCollection
  @CollectionTable(name = "FAVORITE_FOOD")
  private Set<String> favoriteFoods = new HashSet<>();


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
  public Period getPeriod() {
    return period;
  }

  public void setPeriod(Period period) {
    this.period = period;
  }

  public Address getHomeAddress() {
    return homeAddress;
  }

  public void setHomeAddress(Address homeAddress) {
    this.homeAddress = homeAddress;
  }

  public List<AddressEntity> getAddressHistory() {
    return addressHistory;
  }

  public Set<String> getFavoriteFoods() {
    return favoriteFoods;
  }
}
