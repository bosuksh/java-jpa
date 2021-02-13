package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order extends BaseEntity{
  @Id @GeneratedValue
  @Column(name = "ORDER_ID")
  private Long id;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems = new ArrayList<>();

  @OneToOne
  @JoinColumn(name = "DELIVERY_ID")
  private Delivery delivery;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public Member getMember() {
    return member;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public void addOrderItems(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }
}
