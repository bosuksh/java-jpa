package me.doflamingo.domain;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
public class Order {

  @Id @GeneratedValue
  private Long id;
  private int orderAmount;
  private Address address;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(int orderAmount) {
    this.orderAmount = orderAmount;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }
}

